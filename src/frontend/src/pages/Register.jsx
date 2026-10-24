import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../components/AuthLayout";
import ErrorAlert from "../components/ErrorAlert";
import { login, register } from "../api/authApi";
import { useSubmit } from "../hooks/useSubmit";

function Register() {
    const navigate = useNavigate();
    const [form, setForm] = useState({ username: "", email: "", password: "" });
    const { submit, saving, error } = useSubmit();

    function update(e) {
        setForm({ ...form, [e.target.name]: e.target.value });
    }

    async function handleRegister(e) {
        e.preventDefault();
        const registered = await submit(async () => {
            await register(form);
            await login({ username: form.username, password: form.password });
        });
        if (registered) {
            navigate("/rosters");
        }
    }

    return (
        <AuthLayout>
            <h2 className="mb-4 text-center">Register</h2>
            <ErrorAlert message={error} />

            <form onSubmit={handleRegister}>
                <div className="mb-3">
                    <label className="form-label">Username</label>
                    <input name="username" className="form-control" value={form.username} onChange={update} required autoFocus />
                </div>
                <div className="mb-3">
                    <label className="form-label">Email</label>
                    <input type="email" name="email" className="form-control" value={form.email} onChange={update} required />
                </div>
                <div className="mb-4">
                    <label className="form-label">Password</label>
                    <input type="password" name="password" className="form-control" value={form.password} onChange={update} required />
                </div>
                <button type="submit" className="btn btn-success w-100" disabled={saving}>
                    {saving ? "Creating account..." : "Register"}
                </button>
            </form>

            <p className="text-center mt-4 mb-0">
                <small className="text-muted">Already have an account? </small>
                <Link to="/login">Login</Link>
            </p>
        </AuthLayout>
    );
}

export default Register;
