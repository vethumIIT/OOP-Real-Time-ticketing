//import React from "react";

import { Link } from "react-router-dom";
import './Navigation.css'
import { /*useEffect, useState,*/ useContext } from 'react';
import api from '../api/axios'
import { AuthContext } from "../api/AuthContext";


const Navigation = () => {
    const { isLoggedIn, user, logout } = useContext(AuthContext);


    const logoutUser = async (e) => {
        //e.preventDefault();
        if (!isLoggedIn){
            return;
        }
        //alert("Logging out user")
        try {
            const response = await api.get(`/${user["userType"]}/logout`);
            console.log("Data:", response.data);
            //localStorage.setItem("isLoggedIn", "false");
            logout();
            alert("Success");
        } catch (error) {
            console.error("Error fetching data:", error);
            alert("error: "+`${user["userType"]}`);
        }
    };

    return(
        <nav>
            <ul className="navList">
                <li className="listItem">
                <Link to="/" className="listLink">Home</Link>
                </li>

                <li className="listItem">
                <Link to="/menu" className="listLink">Menu</Link>
                </li>

                { !isLoggedIn ? (
                <>
                <li className="listItem">
                <Link to="/login" className="listLink rightLink">Login</Link>
                </li>

                <li className="listItem">
                <Link to="/register" className="listLink rightLink">Register</Link>
                </li>
                </>
                ) : (
                
                <>
                <li className="listItem">
                <Link to="/" className="listLink rightLink" onClick={logoutUser}>Logout</Link>
                </li>
                <li className="listItem">
                <Link to="/dashboard" className="listLink rightLink" >Dashboard</Link>
                </li>
                </>
                )
                }

            </ul>
        </nav>
    )

};

export default Navigation;