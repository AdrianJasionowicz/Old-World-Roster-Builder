import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Modal from "./Modal";
import { deleteAccount } from "../api/accountApi";
import { useSubmit } from "../hooks/useSubmit";

function DeleteAccountModal({ show, onClose }) {
    const navigate = useNavigate();
    const [password, setPassword] = useState("");
    const { submit, saving, error } = useSubmit();

    async function handleDelete() {
        if (await submit(() => deleteAccount({ password }))) {
            navigate("/login");
        }
    }

    return (
        <Modal
            show={show}
            title="Delete Account"
            onClose={onClose}
            onSave={handleDelete}
            saveLabel="Delete account"
            saveVariant="danger"
            saving={saving}
            error={error}
        >
            <div className="alert alert-warning">
                Your account and all your rosters will be deleted. This cannot be undone.
            </div>
            <label className="form-label">Current password</label>
            <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required autoFocus />
        </Modal>
    );
}

export default DeleteAccountModal;
