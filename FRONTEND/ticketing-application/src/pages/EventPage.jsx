import axios from "axios";
import { useParams } from "react-router-dom";
import api from "../api/axios";
import AuthContext from "../api/AuthContext";
import { useContext, useEffect, useState } from "react";

const EventPage = () => {

    const { id } = useParams();
    const { isLoggedIn, user, setUser, login, logout } = useContext(AuthContext);
    const [event, setEvent] = useState({});
    const [ticketCategories, setTicketCategories] = useState(null);
    const [ticketCount, setTicketCount] = useState(1);
    const [ticketCategory, setTicketCategory] = useState(0);
    const [formData, setFormData] = useState({
        "formTicketCount": 0,
        "formTicketCategory": "",
        "formTicketPrice": 0
    });
    const fetchEventData = async () => {
        try {
            const response = await api.post(`/event/get_by_id`, 
                {
                    eventId: id,

                });
            console.log("Data:", response.data);
            console.log("Success");
            setEvent(response.data);
            setTicketCategories(response.data.ticketCategory);
        } catch (error) {
            console.error("Error fetching data:", error);
            console.log("error");
        }
    };

    const handleChange = (e) => {
        var value = e.target.value;
        if(value == ''){
            value=0;
        }
        setTicketCount(parseInt(value));
    }

    const handleSelectChange = (e) => {
        var value = e.target.value;
        setTicketCategory(value);
    }

    const handleFormChange = (e) => {
        var value = e.target.value;
        setFormData({...formData, [e.target.name]: value});

        if(e.target.name == "formTicketCategory"){
            if(ticketCategories != null){
                if(Object.keys(ticketCategories).includes(value)){
                    console.log("Ticket category already exists");
                }
            }
        }
    }

    const handleFormSubmit = (e) => {
        e.preventDefault();
        if(formData["formTicketCategory"] == "" || formData["formTicketCategory"] == null){
            alert("Please enter a valid ticket category");
            return;
        }else if(formData["formTicketPrice"] == "" || formData["formTicketPrice"] <= 0 || formData["formTicketPrice"]==null){
            alert("Please enter a ticket price");
            return;
        }else if(formData["formTicketCount"] == "" || formData["formTicketCount"] <= 0 || formData["formTicketCount"]==null){
            alert("Please enter the number of tickets");
            return;
        }

        if(ticketCategories != null){
            if(Object.keys(ticketCategories).includes(formData["formTicketCategory"]) && formData["formTicketPrice"]!=ticketCategories[formData["formTicketCategory"]][0]){
                alert("you cannot change the price of a ticket category once it is created");
                return;
            }
        }
        addTicketPost();
    }

    const addTicketPost = async () => {
        var data = {
            "eventId": parseInt(id),
            "ticketId": 0,
            "bookingId": 0,
            "ticketCategory": formData["formTicketCategory"],
            "price": parseFloat(formData["formTicketPrice"]),
        }
        try {
            console.log(`/ticket/create?ticketCount=${formData["formTicketCount"]}`);
            console.log(formData["formTicketCount"]);
            const response = await api.post(`/ticket/create?ticketCount=${formData["formTicketCount"]}`, data);
            console.log("Data:", response.data);
            console.log("Success");
            fetchEventData();
        } catch (error) {
            console.error("Error fetching data:", error);
            console.log("error");
        }
    }

    useEffect(() => {
        fetchEventData();
    }, []);

    const bookTickets = async () => {
        if(!isLoggedIn){
            alert("Please login to book tickets");
            return;
        } else if(user["userType"] != "customer"){
            alert("Only customers can book tickets");
            return;
        } 
        if(ticketCount <= 0){
            alert("Please enter a valid ticket count");
            return;
        }
        if(ticketCategory == 0){
            alert("Please select a valid ticket catagory");
            return;
        }
        postData();
    }

    const postData = async () => {
        var data =  {
            "bookingId": 0,
            "eventId": parseInt(id), 
            "customerId": 0,
            "ticketCount": ticketCount,
            "ticketCategory": ticketCategory,
            "bookingDate": new Date().toISOString().split('T')[0]
            }
        try {
            
            const response = await api.post(`/booking/create`, data);
            
            console.log("Data:", response.data);
            console.log("Success");
            alert("Booked tickets")
        } catch (error) {
            console.log(data);
            console.error("Error fetching data:", error);
            console.log("error");
        }
    }
    
    return(
        <>
        <h1>{event.eventName}</h1>
        <ul>
            <li>Event Description: {event.eventDescription}</li>
            <li>Event Date: {event.eventDate}</li>
        </ul>

        <div>
            { isLoggedIn ?
                user["userType"] == "customer" ?(
                <>
                <h3>Book Tickets</h3>
                <select onChange={handleSelectChange}>
                    <option value={0}>Select Catagory</option>
                
                { ticketCategories != null && Object.keys(ticketCategories).map((catagory) => (
                    <option value={catagory} key={catagory}>{catagory} - Rs.{ticketCategories[catagory][0]}</option>
                ))}
                </select>
                <div style={{border: "1px solid black", padding: "10px", width: "50%", margin: "20px"}} >

                    <button onClick={() => setTicketCount(ticketCount + 1)} disabled={ticketCategory == 0}>+ Ticket</button>
                    <button onClick={() => ticketCount >0 ?setTicketCount(ticketCount - 1) :null} disabled={ticketCategory == 0}>- Ticket</button>
                    <ul>
                        <li>Ticket Count: <input type="number" value={ticketCount} onChange={handleChange} disabled={ticketCategory == 0}></input></li>
                        <li>Total Price: Rs.{ticketCount * (ticketCategory==0?0 :ticketCategories[ticketCategory][0])} </li>
                    </ul>
                    <button onClick={bookTickets} disabled={ticketCategory == 0 || ticketCount == 0}>Book Tickets</button>

                </div>
                </>)
                : (
                <>
                <h3>Add Tickets</h3>
                {ticketCategories != null && Object.keys(ticketCategories).length > 0 ? (
                    <>
                    <p>Current Ticket Categories:</p>
                    <ul>
                    { ticketCategories != null && Object.keys(ticketCategories).map((catagory) => (
                        <li key={catagory}>{catagory} - Rs.{ticketCategories[catagory][0]} - {ticketCategories[catagory][1]} tickets</li>
                    ))}
                    </ul>
                    </>
                ):(
                    <p>No tickets found</p>
                )}
                {user["name"]== event.vendorName? (
                <div style={{backgroundColor: "grey", padding: "10px", width: "50%", margin: "20px"}}>
                    
                    <form onSubmit={handleFormSubmit}>
                    <label>Enter Ticket Count:
                    <input type="number" name="formTicketCount" id="formTicketCount" onChange={handleFormChange} placeholder="Enter Ticket Count"/>
                    </label>

                    <label>Enter Ticket Category:
                    <input type="text" name="formTicketCategory" id="formTicketCategory" onChange={handleFormChange} placeholder="Enter Ticket Category"/>
                    </label>

                    <label>Enter Ticket Price(Rs.):
                    <input type="number" name="formTicketPrice" id="formTicketPrice" onChange={handleFormChange} placeholder="Enter Ticket Price"/>
                    </label>
                    <input type="submit" name="submit" id="submit" value="Add Ticket" />
                    </form>
                </div>
                ):(<p>You are not the vendor of this event</p>)}
                </>
                )
            : <p>Please login to view Options</p>}
        </div>
        </>
    )
};

export default EventPage;