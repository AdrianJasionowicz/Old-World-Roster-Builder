import { useNavigate } from "react-router-dom";

import Navbar from "../components/Navbar";

function AdminPanel() {

    const navigate = useNavigate();

    return (

        <>

            <Navbar />

            <div className="container mt-5">

                <h2 className="mb-4">

                    ⚙ Administration Panel

                </h2>

                <div className="row g-4">

                    <div className="col-md-4">

                        <div className="card shadow h-100">

                            <div className="card-body d-flex flex-column">

                                <h4>

                                    ➕ Add Unit

                                </h4>

                                <p className="text-muted">

                                    Create a new unit.

                                </p>

                                <button
                                    className="btn btn-success mt-auto"
                                    onClick={() => navigate("/admin/add-unit")}
                                >
                                    Open
                                </button>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-4">

                        <div className="card shadow h-100">

                            <div className="card-body d-flex flex-column">

                                <h4>

                                    ✏ Modify Unit

                                </h4>

                                <p className="text-muted">

                                    Edit existing units.

                                </p>

                                <button
                                    className="btn btn-warning mt-auto"
                                    onClick={() => navigate("/admin/edit-unit")}
                                >
                                    Open
                                </button>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-4">

                        <div className="card shadow h-100">

                            <div className="card-body d-flex flex-column">

                                <h4>

                                    🗑 Delete Unit

                                </h4>

                                <p className="text-muted">

                                    Remove units.

                                </p>

                                <button
                                    className="btn btn-danger mt-auto"
                                    onClick={() => navigate("/admin/delete-unit")}
                                >
                                    Open
                                </button>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </>

    );

}

export default AdminPanel;