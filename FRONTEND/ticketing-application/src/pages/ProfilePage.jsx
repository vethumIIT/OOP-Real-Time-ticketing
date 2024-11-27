import api from '../api/axios'
import { useState, useContext, useEffect } from 'react';
import AuthContext from "../api/AuthContext";
import "./ProfilePage.css";
import { Link } from 'react-router-dom';

const ProfilePage = () => {

    const { isLoggedIn, user, logout } = useContext(AuthContext);
    const [bookings, setBookings] = useState([]);
    const [events, setEvents] = useState([]);
    const [userData, setUserData] = useState({});
    const [eventFormData, setEventFormData] = useState({});

    const fetchUserData = async () => {
        if (!isLoggedIn){
            console.log("You are not logged in");
            return;
        }
        try {
            const response = await api.post(`/${user["userType"]}/get`,
                {
                    "id":0,
                    "name":user["name"],
                    "email":"",
                    "password":""
                }
            );
            console.log("Data:", response.data);
            console.log("User Data Fetched Successfully");
            setUserData(response.data);
        } catch (error) {
            console.log(error.response.status);
            if(error.response.status == 401 && error.response.data.message == "Not Logged In"){
                console.log("You are not logged in");
                logout();
            }
            console.error("Error fetching data:", error);
            console.log("Error fetching user data");
        }
    };

    const handleEventFormDataChange = (e) => {
        setEventFormData({...eventFormData, [e.target.name]: e.target.value});
    }

    const fetchBookings = async () => {
        if (!isLoggedIn){
            alert("You are not logged in");
            return;
        }
        try {
            const response = await api.post(`/customer/get_bookings_by_name`,{
                "id":0,
                "name":user["name"],
                "email":"",
                "password":""
            });
            console.log("Data:", response.data);
            if (response.data.length > 0){
                console.log("Bookings Fetched Successfully");
                
            }else{
                console.log("No bookings found");
            }
            setBookings(response.data);
        } catch (error) {
            console.log(error.response.status);
            console.log(error.response.data);
            if(error.response.status == 401 && error.response.data=="Not Logged In"){
                console.log("You are not logged in");
                logout();
            }
            console.error("Error fetching data:", error);
            console.log("Error fetching bookings");
        }
    }

    const fetchEvents = async () => {
        if (!isLoggedIn){
            console.log("You are not logged in");
            return;
        }
        try {
            const response = await api.post(`/event/get_vendor_events`,{
                "id":0,
                "name":user["name"],
                "email":"",
                "password":""
            });
            console.log("Data:", response.data);
            if (response.data.length > 0){
                console.log("Events Fetched Successfully");
                setEvents(response.data);
            }else{
                console.log("No events found");
            }
        } catch (error) {
            if(error.response.status == 401 && error.response.data.message == "Not Logged In"){
                logout();
            }
            console.error("Error fetching data:", error);
            console.log("Error fetching events");
        }
    }

    const handleAddEventSubmit = (e) => {
        e.preventDefault();
        console.log("Event added");
        if(!isLoggedIn){
            console.log("You are not logged in");
            return;
        }
        handleAddEvent();
    }

    const handleAddEvent = async () => {
        try{
            const response = await api.post(`/event/create`,{
                "id":0,
                "vendorId":0,
                "eventName":eventFormData["eventName"],
                "eventDate":eventFormData["eventDate"],
                "eventLocation":eventFormData["eventLocation"],
                "eventDescription":eventFormData["eventDescription"]
            }); 
            console.log("Data:", response.data);
            console.log("Event added successfully");
            fetchEvents();
        } catch (error) {
            if(error.response.status == 401 && error.response.data.message == "Not Logged In"){
                logout();
            }
            console.error("Error adding event:", error);
            console.log("Error adding event");
        }
    }

    const handleCancelBooking = async (bookingId) => {
        try{
            const response = await api.post(`/booking/cancel`, {
                "id":0,
                "bookingId":bookingId
            });
            console.log("Data:", response.data);
            console.log("Booking cancelled successfully");
            alert("Booking Cancelled")
            fetchBookings();
        } catch (error) {
            if(error.response.status == 401 && error.response.data.message == "Not Logged In"){
                logout();
            }
            console.error("Error cancelling booking:", error);
            console.log("Error cancelling booking");
        }
    }

    useEffect(() => {
        fetchUserData();
        if(isLoggedIn){
            if (user["userType"] == "customer"){
                fetchBookings();
            }
            else if (user["userType"] == "vendor"){
                fetchEvents();
            }
        }else{
            console.log("You are not logged in");
        }
    }, []);
    
    return(
        <>
        <h1>Dashboard {isLoggedIn?(<>of {user["name"]}</>):(<></>)}</h1>
        <button onClick={fetchUserData}>Test</button>
        <button onClick={fetchBookings}>Fetch Bookings</button>
        {!isLoggedIn?(<><p>Please login to view this page</p></>) 
        : ( <div>
            <ul>
                <li>Name: {userData["name"]}</li>
                <li>Email: {userData["email"]}</li>
                <li>User Type: {user["userType"]}</li>
            </ul>
            {user["userType"] == "customer" ? (
                <>
                
                
                {bookings.length > 0 ? (
                bookings.map((booking) => (
                    <div key={booking.bookingId} className="profileBookingCard">
                        <ul>
                            <li><span>Booking ID:</span> {booking.bookingId}</li>
                            <li><span>Event Name:</span> {booking.eventName}</li>
                            <li><span>Booking Date:</span> {booking.bookingDate}</li>
                            <li><span>Ticket Category:</span> {booking.ticketCategory}</li>
                            <li><span>Ticket Count:</span> {booking.ticketCount}</li>
                        </ul>
                        <button onClick={() => handleCancelBooking(booking.bookingId)}>Cancel Booking</button>
                        
                    </div>
                ))
                
                ) : (
                    <div>No bookings found</div>
                )}
                </>
            ) : ( // if user is not a customer
                <>
                {events.length > 0 ? (
                events.map((event) => (
                    <>
                    <div key={event.eventId} className="profileEventCard">
                        <h5>{event.eventName}</h5>
                        <ul>
                            <li><span>Vendor ID:</span> {event.vendorId}</li>
                            <li><span>Event Date:</span> {event.eventDate}</li>
                            <li><span>Event Location:</span> {event.eventLocation}</li>
                            <li><span>Tickets Sold:</span> {event.ticketsSold}</li>
                            <li><span>Total Tickets:</span> {event.totalTickets}</li>
                            {event.ticketCategory==null?(<>
                            </>):(
                                <li><ul>

                                    {Object.keys(event.ticketCategory).map((category) => (
                                        <li key={category}><span>{category}:</span> Rs.{event.ticketCategory[category][0]} - {event.soldTicketsCategory==null || event.soldTicketsCategory[category]==null ?(<>0</>):(<>{event.soldTicketsCategory[category][1]}</>)}/{event.ticketCategory[category][1]} tickets</li>
                                    ))}

                                    </ul></li>

                            )}
                        </ul>
                        <Link to={`/event/${event.eventId}`}><button>View Event</button></Link>
                    </div>
            
                    </>
                ))
                ) : (
                    <div>No events found for this vendor</div>
                )}
                
                <div style={{width:"50%", margin:"auto", alignItems:"center", alignContent:"left", float:"left"}}>
                    <h4>Add Event</h4>
                    <form id="addEventForm" onSubmit={handleAddEventSubmit} style={{backgroundColor:"grey"}}>
                        <label>Event Name:
                        <input type="text" onChange={handleEventFormDataChange} name="eventName" id="eventName" placeholder="Event Name" />
                        </label>
                        <label>Event Date:
                        <input type="date" onChange={handleEventFormDataChange} name="eventDate" id="eventDate" placeholder="Event Date" />
                        </label>
                        <label>Event Location:
                        <input type="text" onChange={handleEventFormDataChange} name="eventLocation" id="eventLocation" placeholder="Event Location" />
                        </label>
                        <label>Event Description:
                        <input type="text" onChange={handleEventFormDataChange} name="eventDescription" id="eventDescription" placeholder="Event Description" />
                        </label>
                        <input type="submit" value="Add Event"></input>
                    </form>
                </div>
                </>
                
            )}
            </div>
        )}
        </>
    )
};

export default ProfilePage;