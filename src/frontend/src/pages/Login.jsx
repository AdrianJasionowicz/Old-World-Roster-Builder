import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import AuthLayout from "../components/AuthLayout";
import ErrorAlert from "../components/ErrorAlert";
import { login, me } from "../api/authApi";
import { useSubmit } from "../hooks/useSubmit";

function Login() {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const { submit, saving, error } = useSubmit();

    useEffect(() => {
        me().then(() => navigate("/rosters", { replace: true })).catch(() => {});
    }, [navigate]);

    async function handleLogin(e) {
        e.preventDefault();
        if (await submit(() => login({ username, password }))) {
            navigate("/rosters");
        }
    }

    return (
        <AuthLayout>
            <h2 className="mb-4 text-center">Login</h2>
            <ErrorAlert message={error} />

            <form onSubmit={handleLogin}>
                <div className="mb-3">
                    <label className="form-label">Username</label>
                    <input className="form-control" value={username} onChange={e => setUsername(e.target.value)} required autoFocus />
                </div>
                <div className="mb-4">
                    <label className="form-label">Password</label>
                    <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
                </div>
                <button type="submit" className="btn btn-primary w-100" disabled={saving}>
                    {saving ? "Logging in..." : "Login"}
                </button>
            </form>

            <p className="text-center mt-4 mb-0">
                <small className="text-muted">Don't have an account? </small>
                <Link to="/register">Create account</Link>
            </p>
        </AuthLayout>
    );
}

export default Login;
