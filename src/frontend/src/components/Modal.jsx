import "../styles/modal.css";

import ErrorAlert from "./ErrorAlert";

function Modal({ show, title, children, onClose, onSave, saveLabel = "Save", saveVariant = "primary", saving = false, error }) {
    if (!show) {
        return null;
    }

    function submit(e) {
        e.preventDefault();
        onSave();
    }

    return (
        <div className="modal-overlay" onClick={onClose}>
            <form className="modal-window" onSubmit={submit} onClick={e => e.stopPropagation()}>
                <div className="modal-header">
                    <h5 className="mb-0">{title}</h5>
                    <button type="button" className="btn-close" aria-label="Close" onClick={onClose} />
                </div>

                <div className="modal-body">
                    <ErrorAlert message={error} />
                    {children}
                </div>

                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" onClick={onClose}>Cancel</button>
                    <button type="submit" className={`btn btn-${saveVariant}`} disabled={saving}>
                        {saving ? "Saving..." : saveLabel}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default Modal;
