function ErrorAlert({ message, onClose }) {
    if (!message) {
        return null;
    }

    return (
        <div className="alert alert-danger d-flex justify-content-between align-items-start mb-3" role="alert">
            <span>{message}</span>
            {onClose && <button type="button" className="btn-close ms-3" aria-label="Close" onClick={onClose} />}
        </div>
    );
}

export default ErrorAlert;
