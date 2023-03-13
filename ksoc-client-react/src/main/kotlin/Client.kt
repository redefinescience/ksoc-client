import com.kotlineering.ksoc.client.Greeting
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val welcome = Welcome.create {
        name = Greeting().greet()
    }
    createRoot(container).render(welcome)
}