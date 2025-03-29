import axios from "axios";


const api = axios.create({
  baseURL: "https://192.168.1.4:8443/api/",// Backends URL
  withCredentials: true, // Include cookies with requests
  headers: {
    "Content-Type": "application/json"
  },

});

export default api;