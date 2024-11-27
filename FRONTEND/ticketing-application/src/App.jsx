//import { useState } from 'react'
import { Routes, Route, BrowserRouter } from 'react-router-dom';
//import { BrowserRouter } from 'react-router-dom';
//import reactLogo from './assets/react.svg'
//import viteLogo from '/vite.svg'
import './App.css'

import Navigation from "./Components/Navigation"
import HomePage from './pages/HomePage'
import MenuPage from './pages/MenuPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import TestPage from './pages/TestPage';
import EventPage from './pages/EventPage';
import ProfilePage from './pages/ProfilePage';

function App() {
  //const [count, setCount] = useState(0)

  return (


    <BrowserRouter>
    <Navigation/>
      <Routes>
        <Route index element={<HomePage/>}/>
        <Route path='/menu' element={<MenuPage/>}/>
        <Route path='/login' element={<LoginPage/>}/>
        <Route path='/register' element={<RegisterPage/>}/>
        <Route path='/event/:id' element={<EventPage/>}/>
        <Route path='/dashboard' element={<ProfilePage/>}/>

        <Route path='/test' element={<TestPage/>}/>
      </Routes>
    </BrowserRouter>
  
  )
}

export default App
