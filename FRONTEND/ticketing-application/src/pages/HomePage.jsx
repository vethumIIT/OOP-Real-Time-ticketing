//import React from "react";
import { Link } from "react-router-dom";
import './HomePage.css'

const HomePage = () => {
    
    return(
        <>
        <h1>This is the Home page for the ticketing application</h1>
        <div id="mainDiv">
            <div id="buttonDiv">
                <h2>Please Register or Log In to continue</h2>
                <Link id="loginLink" to='/login'><button type="button" id="login">Login</button></Link>
                <Link id="registerLink" to='/register'><button type="button" id="register">Register</button></Link>
            
            </div>
        </div>
        </>
    )

};

export default HomePage;