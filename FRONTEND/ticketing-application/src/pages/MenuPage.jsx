import AuthContext from "../api/AuthContext";
import { useState, useContext, useEffect } from 'react';
import { Link } from "react-router-dom";
import api from '../api/axios'
import './Menu.css'

const MenuPage = () => {
    
    const { isLoggedIn, user, setUser, login, logout } = useContext(AuthContext);
    const [events, setEvents] = useState([]);

    const fetchEvents = async () => {
        try {
            const response = await api.post(`/event/get`);
            console.log("Data:", response.data);
            
            if (response.data) {
                console.log("Response is not empty");
                setEvents(response.data)
            } else {
                console.log("Response is empty");
            }

            console.log("Success");
        } catch (error) {
            console.error("Error fetching data:", error);
            console.log("error");
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);
    
    return(
        <>
        <h1>Upcoming Events</h1>
        
        <div id="mainMenuDiv">
            {events.length > 0 ? (
                events.map((event) => (                
                    <div key={event.id} className="eventDiv">
                    <h3>{event.eventName}</h3>
                    <ul>
                        <li><span>Date: </span>{event.eventDate}</li>
                        <li><span>Location: </span>{event.eventLocation}</li>
                        <br/>
                        <li>{event.eventDescription}</li>
                        
                    </ul>
                    <Link to={`/event/${event.eventId}`}><button className="bookTicketsButton">📙 Book Tickets</button></Link>
                    </div>
                ))
            ) : (
                <h2 style={{color: "grey"}}>No events found</h2>
            )}
            
        </div>

        </>
    )

};

export default MenuPage;