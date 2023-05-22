package com.example.healthyandfoodclean


import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast

class LoadingDialog(val mActivity: Activity) {
    private var isDialog: AlertDialog? = null

    fun startLoading() {
        /**set View*/
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)

        /**set Dialog*/
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog?.show()
    }

    fun isDismiss() {
        try {
            //kiểm tra xem hoạt động hộp thoại có kết thúc hay không
            if (mActivity.isFinishing.not() && isDialog?.isShowing == true) {
                //huy bo
                isDialog?.dismiss()
            }
        } catch (e: IllegalArgumentException) {
            Toast.makeText(mActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
        } finally {

            isDialog = null
        }
    }
}
