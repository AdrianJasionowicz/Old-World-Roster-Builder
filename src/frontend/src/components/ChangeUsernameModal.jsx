import { useState } from "react";

import Modal from "./Modal";
import { changeUsername } from "../api/accountApi";
import { useSubmit } from "../hooks/useSubmit";

function ChangeUsernameModal({ show, onClose, onChanged }) {
    const [newUsername, setNewUsername] = useState("");
    const [password, setPassword] = useState("");
    const { submit, saving, error } = useSubmit();

    async function handleSave() {
        if (await submit(() => changeUsername({ newUsername, password }))) {
            onChanged?.();
            onClose();
        }
    }

    return (
        <Modal show={show} title="Change Username" onClose={onClose} onSave={handleSave} saving={saving} error={error}>
            <div className="mb-3">
                <label className="form-label">New username</label>
                <input className="form-control" value={newUsername} onChange={e => setNewUsername(e.target.value)} required autoFocus />
            </div>
            <div>
                <label className="form-label">Current password</label>
                <input type="password" className="form-control" value={password} onChange={e => setPassword(e.target.value)} required />
            </div>
        </Modal>
    );
}

export default ChangeUsernameModal;
