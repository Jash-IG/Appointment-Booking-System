import { useEffect, useState } from "react";
import { createAppointment, getAppointments } from "../api/appointmentApi.js";
import AppointmentForm from "../components/AppointmentForm.jsx";
import { useNavigate } from "react-router-dom";

export default function BookAppointment() {
  const [appointments, setAppointments] = useState([]);
  const navigate = useNavigate();

  function loadData() {
    getAppointments().then((data) => setAppointments(data));
  }

  useEffect(() => {
    loadData();
  }, []);

  function handleCreate(payload) {
    // Refresh list after booking
    createAppointment(payload).then(() => {
      loadData();
      navigate("/list");
    });
  }

  return (
    <div style={{ padding: "20px" }}>
      <h2>Book Appointment</h2>

      <div style={{ margin: "10px 0" }}>
        <button onClick={() => navigate("/")}>Back Home</button>
        <button style={{ marginLeft: "10px" }} onClick={() => navigate("/list")}>
          Go To List
        </button>
      </div>

      <AppointmentForm
        existingAppointments={appointments}
        onCreate={handleCreate}
      />
    </div>
  );
}
