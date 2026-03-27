function format12h(time24) {
  // time24 like "14:30"
  if (!time24) return "";

  const hh = parseInt(time24.split(":")[0], 10);
  const mm = time24.split(":")[1];

  let hour = hh;
  let ampm = "AM";

  if (hh === 0) {
    hour = 12;
    ampm = "AM";
  } else if (hh === 12) {
    hour = 12;
    ampm = "PM";
  } else if (hh > 12) {
    hour = hh - 12;
    ampm = "PM";
  }

  // Keep 2-digit minutes already
  return `${hour}:${mm} ${ampm}`;
}

export default function TimeSlotPicker({ startTime, endTime, onStartChange, onEndChange, timeSlot }) {
  return (
    <div style={{ marginBottom: "10px" }}>
      <div>
        <label>Start Time: </label>
        <input type="time" value={startTime} onChange={(e) => onStartChange(e.target.value)} />
      </div>

      <div style={{ marginTop: "8px" }}>
        <label>End Time: </label>
        <input type="time" value={endTime} onChange={(e) => onEndChange(e.target.value)} />
      </div>

      <div style={{ marginTop: "8px", color: "#444" }}>
        <b>time_slot:</b> {timeSlot ? timeSlot : "(select start & end)"}
      </div>
    </div>
  );
}

export { format12h };
