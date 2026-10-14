import { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

import { exportArmyPdf } from "../api/armyApi";
import { logout, me } from "../api/authApi";

function Navbar({ army }) {

    const navigate = useNavigate();

    const location = useLocation();

    const [user, setUser] = useState(null);

    const isBuilder = location.pathname.startsWith("/builder");

    const isAccount = location.pathname === "/account";

    const isAdmin = location.pathname === "/admin";

    const showRosterButton = isBuilder || isAccount || isAdmin;

    useEffect(() => {

        loadUser();

    }, []);

    async function loadUser() {

        try {

            const response = await me();

            setUser(response.data);

        } catch (e) {

            console.error(e);

        }

    }

    async function handleLogout() {

        try {

            await logout();

        } catch (e) {

            console.error(e);

        }

        navigate("/login");

    }

    async function handleExportPdf() {

        try {

            const response = await exportArmyPdf(
                army.name,
                army.id
            );

            const blob = new Blob(
                [response.data],
                { type: "application/pdf" }
            );

            const url = window.URL.createObjectURL(blob);

            window.open(url, "_blank");

        } catch (e) {

            console.error(e);

        }

    }

    return (

        <nav className="navbar navbar-dark bg-dark">

            <div className="container-fluid">

                <div className="d-flex align-items-center gap-3">

                    {

                        showRosterButton &&

                        <button

                            className="btn btn-outline-light"

                            onClick={() => navigate("/rosters")}

                        >

                            ← Rosters

                        </button>

                    }

                    <span className="navbar-brand fw-bold fs-3 mb-0">

                        ⚔ Old World Roster Builder

                    </span>

                </div>

                <div className="d-flex align-items-center gap-3">

                    {

                        army &&

                        <>

                            {

                                isBuilder &&

                                <button

                                    className="btn btn-outline-warning"

                                    onClick={handleExportPdf}

                                >

                                    📄 Export PDF

                                </button>

                            }

                            <div className="text-end text-light">

                                <div>

                                    <strong>

                                        {army.name}

                                    </strong>

                                </div>

                                <small>

                                    {army.pointsLimit} Points

                                </small>

                            </div>

                        </>

                    }

                    <div className="text-light">

                        👤 {user?.username ?? "Loading..."}

                    </div>

                    <button

                        className="btn btn-outline-info"

                        disabled={isAccount}

                        onClick={() => navigate("/account")}

                    >

                        Account

                    </button>

                    {

                        user?.role === "ROLE_ADMIN" &&

                        <button

                            className="btn btn-outline-success"

                            disabled={isAdmin}

                            onClick={() => navigate("/admin")}

                        >

                            ⚙ Administration

                        </button>

                    }

                    <button

                        className="btn btn-outline-danger"

                        onClick={handleLogout}

                    >

                        Logout

                    </button>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;