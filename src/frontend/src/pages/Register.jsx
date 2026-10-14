import { useState } from "react";
import { Link } from "react-router-dom";
import { register } from "../api/authApi";
import AuthLayout from "../components/AuthLayout";

function Register() {

    const [form, setForm] = useState({
        username: "",
        email: "",
        password: ""
    });

    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    };

    const handleRegister = async (e) => {

        e.preventDefault();

        setMessage("");
        setError("");

        try {

            await register(form);

            setMessage("✅ Account created successfully!");

            setForm({
                username: "",
                email: "",
                password: ""
            });

        } catch (err) {

            console.error(err);

            setError("❌ Registration failed.");

        }

    };

    return (

        <AuthLayout>

            <h2 className="mb-4 text-center">
                Register
            </h2>

            {message &&
                <div className="alert alert-success">
                    {message}
                </div>
            }

            {error &&
                <div className="alert alert-danger">
                    {error}
                </div>
            }

            <form onSubmit={handleRegister}>

                <div className="mb-3">

                    <label className="form-label">
                        Username
                    </label>

                    <input
                        name="username"
                        className="form-control"
                        value={form.username}
                        onChange={handleChange}
                        required
                    />

                </div>

                <div className="mb-3">

                    <label className="form-label">
                        Email
                    </label>

                    <input
                        type="email"
                        name="email"
                        className="form-control"
                        value={form.email}
                        onChange={handleChange}
                        required
                    />

                </div>

                <div className="mb-4">

                    <label className="form-label">
                        Password
                    </label>

                    <input
                        type="password"
                        name="password"
                        className="form-control"
                        value={form.password}
                        onChange={handleChange}
                        required
                    />

                </div>

                <button
                    type="submit"
                    className="btn btn-success w-100">

                    Register

                </button>

            </form>

            <div className="text-center mt-4">

                <small className="text-muted">

                    Already have an account?

                </small>

                <br />

                <Link
                    to="/login"
                    className="btn btn-link text-decoration-none">

                    Login

                </Link>

            </div>

        </AuthLayout>

    );

}

export default Register;