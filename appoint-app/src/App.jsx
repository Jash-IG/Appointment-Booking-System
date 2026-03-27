import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home.jsx";
import AppointmentsList from "./pages/AppointmentsList.jsx";
import BookAppointment from "./pages/BookAppointment.jsx";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/list" element={<AppointmentsList />} />
        <Route path="/book" element={<BookAppointment />} />
      </Routes>
    </BrowserRouter>
  );
}