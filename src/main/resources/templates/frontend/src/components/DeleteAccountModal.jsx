import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Modal from "./Modal";

import { deleteAccount } from "../api/accountApi";

function DeleteAccountModal({ show, onClose }) {

    const navigate = useNavigate();

    const [password, setPassword] = useState("");

    async function handleDelete() {

        try {

            await deleteAccount({

                password

            });

            alert("Account deleted successfully.");

            navigate("/login");

        } catch (e) {

            console.error(e);

            alert("Unable to delete account.");

        }

    }

    return (

        <Modal

            show={show}

            title="⚠ Delete Account"

            onClose={onClose}

            onSave={handleDelete}

        >

            <div className="alert alert-danger">

                <strong>

                    Warning!

                </strong>

                <br />

                This action cannot be undone.

            </div>

            <div>

                <label className="form-label">

                    Current Password

                </label>

                <input

                    type="password"

                    className="form-control"

                    value={password}

                    onChange={e => setPassword(e.target.value)}

                    placeholder="Enter your password"

                />

            </div>

        </Modal>

    );

}

export default DeleteAccountModal;