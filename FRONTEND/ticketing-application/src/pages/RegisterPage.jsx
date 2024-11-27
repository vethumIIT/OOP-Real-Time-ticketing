import './form.css';
import { useState } from 'react';
import { useEffect } from "react";
import api from '../api/axios';

const RegisterPage = () => {
    const [passwordColour, setPassowordColour] = useState();
    const [confirmPasswordColour, setConfirmPassowordColour] = useState({});

    const [input, setInputs] = useState({
        "name":"",
        "email":"",
        "passwd":"",
        "confirmPasswd":"",
        "userType":""
    });
    

    const handleChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        var match = false;

        setInputs(values => ({...values, [name]: value}))

        console.log(`password: ${input["passwd"]}`);
        console.log(`ConfirmPassword: ${input["confirmPasswd"]}`)
        console.log(`password Event: ${value}`);

        if (name=="passwd"){
            match = input["confirmPasswd"]==value
        }else if(name=="confirmPasswd"){
            match = input["passwd"]==value
        }

        if(name=="passwd" || name=="confirmPasswd"){
            if(!match){
                setConfirmPassowordColour({
                    borderColor: 'red'
                })
            }else{
                setConfirmPassowordColour({})
            }
        }


    };

    const handleSubmit = (event) =>{
        event.preventDefault();

        if(input["userType"]==null || input["userType"] == ""){
            alert("Please enter your userType");
            return;
        }else if(input["name"]==null || input["name"] == ""){
            alert("Please enter your username");
            return;
        } else if(input["email"]==null || input["email"] == ""){
            alert("Please enter your email");
            return;
        } else if(input["passwd"]==null || input["passwd"] == ""){
            alert("Please enter your password");
            return;
        }else if(input["passwd"]!=input["confirmPasswd"]){
            alert(input["passwd"]);
            alert(input["confirmPasswd"]);
            alert("Passwords do not match!");
            return;
        }

        sendPost();

        alert(`You entered the following Info:\n\
            name: ${input["name"]}\n\
            email: ${input["email"]}\n\
            password: ${input["passwd"]}\n\
            Confirm Password: ${input["confirmPasswd"]}`);

    }

    const sendPost = async () => {
        try {
            const response = await api.post(`/${input["userType"]}/register`,
                {
                    "id":0,
                    "name":input["name"],
                    "email":input["email"],
                    "password":input["passwd"]
                }
            );
            console.log("Data:", response.data);
            alert("Success: "+response.data);
            if(response.data == "Success"){
                alert("Your account was registered successfully!")
            }else if(response.data == "Exists"){
                alert("An account with that name already exists!")
            }

        } catch (error) {
            console.error("Error fetching data:", error);
            alert("error");
        }
    };


    
    return(
        <>
        <h1>This is the Register page for the ticketing application</h1>
        <div id="mainDiv">
            <div id="formDiv">
                <h2>Register</h2>
                <form method='POST' onSubmit={handleSubmit}>
                    <label htmlFor="userType">Are you a Vendor or Customer?:</label>
                    <select name="userType" id="userType" onChange={handleChange}>
                        <option value="">-- Select --</option>
                        <option value="customer">Customer</option>
                        <option value="vendor">Vendor</option>
                    </select>
                    <label htmlFor="name">Enter your name:<br/>
                        <input type="text" name="name" id="name" onChange={handleChange}/>
                    </label>
                    <label htmlFor="email">Enter your email:<br/>
                        <input type="email" name="email" id="email" onChange={handleChange}/>
                    </label>
                    <label htmlFor="passwd">Enter your password: <br/>
                        <input type="password" name="passwd" id="passwd" onChange={handleChange}/>
                    </label>
                    <label htmlFor="confirmPasswd">Confirm your password: <br/>
                        <input type="password" name="confirmPasswd" id="confirmPasswd" onChange={handleChange} style={confirmPasswordColour}/>
                    </label>
                    <br/>
                    <input type='Submit' name='submit' id='submit' value="Register"></input>
                </form>

            </div>
        </div>
        </>
    )

};

export default RegisterPage;