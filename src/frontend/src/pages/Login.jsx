import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import { login, me } from "../api/authApi";
import AuthLayout from "../components/AuthLayout";

function Login() {

    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async (e) => {

        e.preventDefault();

        setMessage("");
        setError("");

        try {

            await login({

                username,
                password

            });

            const response = await me();

            setMessage(`✅ Welcome ${response.data.username}!`);

            setTimeout(() => {

                navigate("/rosters");

            }, 500);

        } catch (err) {

            console.error(err);

            setError("❌ Invalid username or password.");

        }

    };

    return (

        <AuthLayout>

            <h2 className="mb-4 text-center">

                Login

            </h2>

            {

                message &&

                <div className="alert alert-success">

                    {message}

                </div>

            }

            {

                error &&

                <div className="alert alert-danger">

                    {error}

                </div>

            }

            <form onSubmit={handleLogin}>

                <div className="mb-3">

                    <label className="form-label">

                        Username

                    </label>

                    <input

                        type="text"
                        className="form-control"

                        value={username}

                        onChange={(e) => setUsername(e.target.value)}

                        required

                    />

                </div>

                <div className="mb-4">

                    <label className="form-label">

                        Password

                    </label>

                    <input

                        type="password"

                        className="form-control"

                        value={password}

                        onChange={(e) => setPassword(e.target.value)}

                        required

                    />

                </div>

                <button

                    type="submit"

                    className="btn btn-primary w-100">

                    Login

                </button>

            </form>

            <div className="text-center mt-4">

                <small className="text-muted">

                    Don't have an account?

                </small>

                <br/>

                <Link

                    to="/register"

                    className="btn btn-link text-decoration-none">

                    Create account

                </Link>

            </div>

        </AuthLayout>

    );

}

export default Login;