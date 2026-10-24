import { Link } from "react-router-dom";

import Navbar from "../components/Navbar";

const ACTIONS = [
    { to: "/admin/add-unit", icon: "bi-plus-circle", title: "Add Unit", text: "Create a unit with stats, special rules and upgrades.", variant: "success" },
    { to: "/admin/delete-unit", icon: "bi-trash", title: "Delete Unit", text: "Remove a unit that is not used in any roster.", variant: "danger" }
];

function AdminPanel() {
    return (
        <div className="page">
            <Navbar />

            <div className="container mt-5">
                <h2 className="mb-4">Administration</h2>

                <div className="row g-4">
                    {ACTIONS.map(action => (
                        <div key={action.to} className="col-md-6 col-lg-4">
                            <div className="card h-100">
                                <div className="card-body d-flex flex-column">
                                    <h4><i className={`bi ${action.icon} me-2`} />{action.title}</h4>
                                    <p className="text-muted">{action.text}</p>
                                    <Link to={action.to} className={`btn btn-${action.variant} mt-auto`}>Open</Link>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default AdminPanel;
