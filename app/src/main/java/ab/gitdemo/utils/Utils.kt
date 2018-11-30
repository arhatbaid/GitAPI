package ab.gitdemo.utils

import ab.gitdemo.R
import ab.gitdemo.ui.base.ActBase
import android.content.Context
import android.net.ConnectivityManager
import android.text.format.DateFormat
import android.util.Patterns
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Utils {

     val EMAIL_PATTERN = Pattern.compile(
             "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
             "\\@" +
             "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
             "(" +
             "\\." +
             "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
             ")+"
     )

    fun animation(view: View, context: Context, animRes: Int) {
        view.animation = AnimationUtils.loadAnimation(context, animRes)
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 6) {
            return false
        }
        val regex = "^[a-zA-Z0-9]+$"

        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

      fun isEmailValid(email: String): Boolean {
          return EMAIL_PATTERN.matcher(email).matches()
      }

      fun isConnectedToNetwork(context: Context): Boolean {
          val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
          val activeNetwork = cm.activeNetworkInfo
          return activeNetwork != null && activeNetwork.isConnected
      }

      fun hideKeyboard(context: ActBase) {
          val view = context.currentFocus
          if (view != null) {
              val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
              imm.hideSoftInputFromWindow(view.windowToken, 0)
          }
      }

      fun hideKeyboard(view: View) {
          if (view != null) {
              val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
              imm.hideSoftInputFromWindow(view.windowToken, 0)
          }
      }

      fun getDate(): List<Int>{
          val c = Calendar.getInstance().getTime()
          val df = SimpleDateFormat("MM/dd/yyyy")
          return df.format(c).split("/").map { it.toInt() }
      }

      fun dateToTimeStamp(date: String): Long{
          val df = SimpleDateFormat("MM/dd/yyyy")
          return Timestamp((df.parse(date)).time).time
      }

      fun timestampToDate(timestamp: Long): String{
          val cal = Calendar.getInstance(Locale.ENGLISH)
          cal.timeInMillis = timestamp * 1000L
          return DateFormat.format("dd MMM yy'\n'hh:mm", cal).toString()
      }

    fun getColorDrawable(pos: Int): Int {
        val colors = intArrayOf(R.drawable.circular_view_purple, R.drawable.circular_view_red, R.drawable.circular_view_green, R.drawable.circular_view_blue, R.drawable.circular_view_light_red)
        return colors[pos]
    }
}