// Returns true if date + time_slot is already used (ignoring CANCELLED)
export function isSlotAlreadyBooked(existingAppointments, appointment_date, time_slot) {
  for (let i = 0; i < existingAppointments.length; i++) {
    const a = existingAppointments[i];

    if (
      a.appointment_date === appointment_date &&
      a.time_slot === time_slot &&
      a.status !== "CANCELLED"
    ) {
      return true;
    }
  }
  return false;
}
