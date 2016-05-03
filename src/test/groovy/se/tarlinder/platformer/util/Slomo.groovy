package se.tarlinder.platformer.util

class Slomo {
    public static slomo(Closure closure) {
        closure()
        Thread.sleep(50)
    }
}
