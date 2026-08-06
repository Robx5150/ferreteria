import { toast } from "react-toastify";

type NavigateFn = (path: string) => void;

export const ToastService = {
  successRedirect(message: string, navigate: NavigateFn, redirectTo: string) {
    toast.success(
      ({ closeToast }) => (
        <div>
          <p>{message}</p>

          <button
            onClick={() => {
              closeToast?.();
              navigate(redirectTo);
            }}
            className="mt-2 px-3 py-1 bg-green-600 text-white rounded"
          >
            Cerrar
          </button>
        </div>
      ),
      {
        className: "w-96 p-4 rounded-lg shadow-lg bg-white text-black",
        autoClose: false,
      },
    );
  },

  error(message: string) {
    toast.error(message);
  },

  loading(message: string) {
    toast.info(message, { autoClose: false });
  },
};
