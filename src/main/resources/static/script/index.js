/** @type {HTMLDivElement} */
const toastList = document.getElementById("toastList")

/**
 * Toast 추가
 * 
 * @param msg {string}
 * @param isError {boolean}
 */
const addToast = (msg, isError = false) => {
    const newToast = document.createElement("div");
    newToast.classList.add('toast');
    newToast.setAttribute('role', 'alert');
    newToast.setAttribute('aria-live', 'assertive');
    newToast.setAttribute('aria-atomic', 'true');

    if (isError) {
        newToast.classList.add('toast-error');
    } else {
        newToast.classList.add('toast-success');
    }

    newToast.innerHTML = `
        <div class="toast-body">
          <strong class="me-auto">${msg}</strong>
        </div>
      `;

    toastList.appendChild(newToast);

    const toast = new bootstrap.Toast(newToast);

    toast.show();

    setTimeout(() => {
        toast.dispose();
        newToast.remove()
    }, 3000);
};

/**
 * 체크박스 체크 여부 확인
 *
 * @param elementId {string} id of input element whose type is checkbox
 * @returns {boolean}
 */
const isCheck = elementId => {
    /** @type {HTMLInputElement} */
    const checkbox = document.getElementById(elementId);
    return checkbox.checked;
}

/**
 * 웹후크 등록 처리
 */
const registerWebhook = () => {
    /** @type {HTMLInputElement} */
    const urlInput = document.getElementById("webhook-url");

    axios.post("/api/subscribe", {
        url: urlInput.value,
        main: isCheck("notice_main"),
        library: isCheck("notice_library"),
        instagram: isCheck("notice_instagram"),
        student: isCheck("notice_student"),
        sanhak: isCheck("notice_sanhak"),
        sw: isCheck("notice_sw")
    })
        .then(res => res.data)
        .then(data => {
            addToast(data.message, data.code === -1)
        })
        .catch(err => {
            console.error(err)
            addToast(err.message, true)
        })
}

// 이벤트 리스너 등록
/** @type {HTMLButtonElement}*/
const subscribeBtn = document.getElementById("subscribe")
subscribeBtn.addEventListener("click", registerWebhook)