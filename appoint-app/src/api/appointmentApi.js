const BASE_URL = "http://localhost:8087/appointments/";

export async function getAppointments() {
  const res = await fetch(BASE_URL);
  return await res.json();
}

export async function deleteAppointment(id) {
  await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
}

export async function createAppointment(payload) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  return await res.json();
}