package com.example.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.DayBinder
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private val eventsMap = HashMap<LocalDate, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)

        // 샘플 일정
        eventsMap[LocalDate.now()] = "회의"
        eventsMap[LocalDate.now().plusDays(2)] = "프로젝트 마감"

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(12)
        val lastMonth = currentMonth.plusMonths(12)
        val firstDayOfWeek = DayOfWeek.SUNDAY

        calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer {
                return DayViewContainer(view)
            }
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.textView.text = day.date.dayOfMonth.toString()

                if (day.position == DayPosition.MonthDate) {
                    val event = eventsMap[day.date]
                    if (event != null) {
                        container.eventTextView.text = event
                        container.eventTextView.visibility = View.VISIBLE
                    } else {
                        container.eventTextView.visibility = View.GONE
                    }
                } else {
                    container.textView.text = ""
                    container.eventTextView.visibility = View.GONE
                }
            }
        }

        // DayViewContainer에서 사용할 레이아웃 지정
        calendarView.dayViewResource = R.layout.calendar_day_layout
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
        val eventTextView: TextView = view.findViewById(R.id.calendarEventText)
    }
}
