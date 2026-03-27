import { useState } from "react";
import DatePicker from "./DatePicker.jsx";
import TimeSlotPicker, { format12h } from "./TimeSlotPicker.jsx";
import { isSlotAlreadyBooked } from "./slotConstraint.jsx";

function nowDateTimeString() {
  // "YYYY-MM-DD HH:MM:SS" (simple)
  const d = new Date();
  const yyyy = d.getFullYear();
  const mm = String(d.getMonth() + 1).padStart(2, "0");
  const dd = String(d.getDate()).padStart(2, "0");
  const hh = String(d.getHours()).padStart(2, "0");
  const mi = String(d.getMinutes()).padStart(2, "0");
  const ss = String(d.getSeconds()).padStart(2, "0");
  return `${yyyy}-${mm}-${dd} ${hh}:${mi}:${ss}`;
}

export default function AppointmentForm({ existingAppointments, onCreate }) {
  const [id, setId] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [appointmentDate, setAppointmentDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [status, setStatus] = useState("BOOKED");
  const [createdAt, setCreatedAt] = useState(nowDateTimeString());

  // build slot string like: "10:00 AM - 10:30 AM"
  const timeSlot =
    startTime && endTime ? `${format12h(startTime)} - ${format12h(endTime)}` : "";

  function handleSubmit(e) {
    e.preventDefault();

    if (!name || !email || !appointmentDate || !startTime || !endTime) {
      alert("Please fill all fields");
      return;
    }

    // simple time validation
    if (endTime <= startTime) {
      alert("End time must be greater than start time");
      return;
    }

    // constraint check: same date + time_slot not allowed
    const taken = isSlotAlreadyBooked(existingAppointments, appointmentDate, timeSlot);
    if (taken) {
      alert("Already filled for this date and time");
      return;
    }

    const payload = {
      name: name,
      email: email,
      appointment_date: appointmentDate,
      time_slot: timeSlot,
      status: status,
      created_at: createdAt,
    };

    // optional: include id if user typed it
    if (id) {
      payload.id = parseInt(id, 10);
    }

    onCreate(payload);
  }

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "420px" }}>
      <div style={{ marginBottom: "10px" }}>
        <label>Id: </label>
        <input value={id} onChange={(e) => setId(e.target.value)} />
      </div>

      <div style={{ marginBottom: "10px" }}>
        <label>Name: </label>
        <input value={name} onChange={(e) => setName(e.target.value)} />
      </div>

      <div style={{ marginBottom: "10px" }}>
        <label>Email: </label>
        <input value={email} onChange={(e) => setEmail(e.target.value)} />
      </div>

      <DatePicker value={appointmentDate} onChange={setAppointmentDate} />

      <TimeSlotPicker
        startTime={startTime}
        endTime={endTime}
        onStartChange={setStartTime}
        onEndChange={setEndTime}
        timeSlot={timeSlot}
      />

      <div style={{ marginBottom: "10px" }}>
        <label>Status: </label>
        <select value={status} onChange={(e) => setStatus(e.target.value)}>
          <option value="BOOKED">BOOKED</option>
          <option value="CANCELLED">CANCELLED</option>
          <option value="COMPLETED">COMPLETED</option>
        </select>
      </div>

      <div style={{ marginBottom: "10px" }}>
        <label>Created At: </label>
        <input value={createdAt} readOnly />
        <button type="button" style={{ marginLeft: "8px" }} onClick={() => setCreatedAt(nowDateTimeString())}>
          Refresh
        </button>
      </div>

      <button type="submit">Book Appointment</button>
    </form>
  );
}
