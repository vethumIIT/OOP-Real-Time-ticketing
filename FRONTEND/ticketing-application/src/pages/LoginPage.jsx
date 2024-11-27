import './form.css';
import api from '../api/axios'
import { useState, useContext } from 'react';
//import ReactDOM from 'react-dom/client';
import { AuthContext } from "../api/AuthContext";

const LoginPage = () => {
    const { isLoggedIn, user, setUser, login, logout } = useContext(AuthContext);
    const [input, setInputs] = useState({
        "userType": "",
        "name": "",
        "passwd": ""
    });

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;

        setInputs(values => ({...values, [name]: value}))

    };

    const fetchData = async () => {
        try {
            const response = await api.get("/");
            console.log("Data:", response.data);
            alert("Success");
        } catch (error) {
            console.error("Error fetching data:", error);
            alert("error");
        }
    };

    const sendPost = async () => {
        try {
            const response = await api.post("/"+input["userType"]+"/login",
                {
                    "id":0,
                    "name":input["name"],
                    "email":"",
                    "password":input["passwd"]
                }
            );
            console.log("Data:", response.data);
            if (response.data == "Not Found"){
                alert("There is no user with this name")
            }
            else if(response.data == "Incorrect Password"){
                alert("The password you entered is incorrect")

            }else if(response.data == "Logged In"){
                alert("You were logged in successfully!")
                login(input);
            }
        } catch (error) {
            console.error("Error fetching data:", error);
            alert("uh oh")
            alert("An Unexpected error occured while trying to login");
            alert(error.response.data)
            alert(":(")
            return "An unexpected error occured"
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        if (input["name"]==null || input["name"]==""){
            alert("Please Enter your username");
            return
        }
        if (input["userType"]==null || input["userType"]==""){
            alert("Please Enter your user type");
            return
        }
        if (input["passwd"]==null || input["passwd"]==""){
            alert("Please Enter your password");
            return
        }

        sendPost();
    }

    return(
        <>
        <h1>This is the Login page for the ticketing application</h1>
        <div id="mainDiv">
            <div id="formDiv">
                <h2>Log In</h2>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="userType">Are you a Vendor or Customer?:</label>
                    <select name="userType" id="userType" onChange={handleChange}>
                    <option value="">-- Select --</option>
                        <option value="customer">Customer</option>
                        <option value="vendor">Vendor</option>
                    </select><br/>
                    <label htmlFor="name">Enter your name:<br/>
                        <input type="text" name="name" id="name" onChange={handleChange}/>
                    </label>
                    <label htmlFor="passwd">Enter your password: <br/>
                        <input type="password" name="passwd" id="passwd" onChange={handleChange}/>
                    </label>
                    <br/>
                    <input type='Submit' name='submit' id='submit' value="Log In"></input>
                </form>

            </div>
        </div>
        </>
    )
};

export default LoginPage;