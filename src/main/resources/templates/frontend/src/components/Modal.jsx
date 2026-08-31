import "../styles/modal.css";

function Modal({

    show,

    title,

    children,

    onClose,

    onSave

}) {

    if (!show) {

        return null;

    }

    return (

        <div className="modal-overlay">

            <div className="modal-window">

                <div className="modal-header">

                    <h5 className="mb-0">

                        {title}

                    </h5>

                    <button
                        className="btn-close"
                        onClick={onClose}
                    />

                </div>

                <div className="modal-body">

                    {children}

                </div>

                <div className="modal-footer">

                    <button

                        className="btn btn-secondary"

                        onClick={onClose}

                    >

                        Cancel

                    </button>

                    <button

                        className="btn btn-primary"

                        onClick={onSave}

                    >

                        Save

                    </button>

                </div>

            </div>

        </div>

    );

}

export default Modal;