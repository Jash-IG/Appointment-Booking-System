import { useNavigate } from "react-router-dom";

export default function Home() {
  const navigate = useNavigate();

  return (
    <div style={{ padding: "20px" }}>
      <h2>Appointment Service</h2>

      <div style={{ display: "flex", gap: "10px", marginTop: "20px" }}>
        <button onClick={() => navigate("/list")}>Go To List</button>
        <button onClick={() => navigate("/book")}>Book Appointment</button>
      </div>

      <p style={{ marginTop: "25px", color: "#666" }}>
        Home page content you will fill later.
      </p>
    </div>
  );
}