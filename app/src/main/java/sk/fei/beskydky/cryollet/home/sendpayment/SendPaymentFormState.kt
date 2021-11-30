package sk.fei.beskydky.cryollet.home.sendpayment

class SendPaymentFormState(){
    var keyValid = false
    var amountValid = false
    var currencyValid = false


    fun isValid(): Boolean{
        return keyValid && amountValid && currencyValid
    }
}