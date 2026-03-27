export default function AppointmentTable({ appointments, onDelete }) {
  return (
    <table border="1" cellPadding="8" style={{ borderCollapse: "collapse", width: "100%" }}>
      <thead>
        <tr>
          <th>id</th>
          <th>name</th>
          <th>email</th>
          <th>appointment_date</th>
          <th>time_slot</th>
          <th>status</th>
          <th>created_at</th>
          <th>completed?</th>
          <th>action</th>
        </tr>
      </thead>

      <tbody>
        {appointments.map((a) => (
          <tr key={a.id}>
            <td>{a.id}</td>
            <td>{a.name}</td>
            <td>{a.email}</td>
            <td>{a.appointment_date}</td>
            <td>{a.time_slot}</td>
            <td>{a.status}</td>
            <td>{a.created_at}</td>
            <td style={{ textAlign: "center" }}>
              <input type="checkbox" checked={a.status === "COMPLETED"} readOnly />
            </td>
            <td>
              <button onClick={() => onDelete(a.id)}>Delete</button>
            </td>
          </tr>
        ))}

        {appointments.length === 0 && (
          <tr>
            <td colSpan="9" style={{ textAlign: "center" }}>
              No appointments found
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
}