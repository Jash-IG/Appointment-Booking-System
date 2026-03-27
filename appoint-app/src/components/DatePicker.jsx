export default function DatePicker({ value, onChange }) {
  return (
    <div style={{ marginBottom: "10px" }}>
      <label>Date: </label>
      <input
        type="date"
        value={value}
        onChange={(e) => onChange(e.target.value)}
      />
    </div>
  );
}
