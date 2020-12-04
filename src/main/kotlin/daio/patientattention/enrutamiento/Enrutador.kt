package daio.patientattention.enrutamiento

import daio.patientattention.model.Address
import daio.patientattention.managerBD.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import java.time.LocalDateTime


class Enrutador {

    @Autowired private lateinit var  sender: JavaMailSender
    @Autowired private lateinit var addressRepository: AddressRepository

    private val lastAlarm: HashMap<String, LocalDateTime> = HashMap()
    private val minutesDelay: Long = 5

    fun getAddresses(): List<Address> {
        return addressRepository.findAll()
    }

    fun body (patientId: String, location: String): String {
        return "El paciente con ID: $patientId está" +
                " en estado crítico y debe ser atendido inmediatamente en la dirección " +
                location
    }

    fun subject (patientId: String): String {
        return "Estado crítico de paciente $patientId"
    }

    fun sendEmailTool (textMessage: String, email: String, subject: String) {
        val message = sender.createMimeMessage()
        val helper = MimeMessageHelper(message)
        helper.setTo(email)
        helper.setText(textMessage)
        helper.setSubject(subject)
        sender.send(message)
    }

    fun sendEmails(addresses: List<Address>, patientId: String, location: String) {
        val BODY = body(patientId, location)
        val SUBJECT = subject(patientId)
        for (add in addresses) {
            sendEmailTool(BODY, add.mail, SUBJECT)
        }
    }

    fun raiseAlarmState(patientId: String, location: String) {
        val now = LocalDateTime.now()

        if (lastAlarm.containsKey(patientId))
            if (lastAlarm[patientId]!!.plusMinutes(minutesDelay).isAfter(now))
                return

        lastAlarm[patientId] = now

        val addresses = getAddresses()
        sendEmails(getAddresses(), patientId, location)
    }

    fun postAddress (address: Address): Address {
        return addressRepository.save(address)
    }

}