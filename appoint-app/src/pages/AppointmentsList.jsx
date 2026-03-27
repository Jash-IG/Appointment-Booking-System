import { useEffect, useState } from "react";
import { getAppointments, deleteAppointment } from "../api/appointmentApi.js";
import AppointmentTable from "../components/AppointmentTable.jsx";
import { useNavigate } from "react-router-dom";

export default function AppointmentsList() {
  const [appointments, setAppointments] = useState([]);
  const navigate = useNavigate();

  function loadData() {
    getAppointments().then((data) => setAppointments(data));
  }

  useEffect(() => {
    loadData();
  }, []);

  function handleDelete(id) {
    deleteAppointment(id).then(() => loadData());
  }

  return (
    <div style={{ padding: "20px" }}>
      <h2>Appointments List</h2>

      <div style={{ margin: "10px 0" }}>
        <button onClick={() => navigate("/")}>Back Home</button>
        <button style={{ marginLeft: "10px" }} onClick={() => navigate("/book")}>
          Book New
        </button>
      </div>

      <AppointmentTable appointments={appointments} onDelete={handleDelete} />
    </div>
  );
}