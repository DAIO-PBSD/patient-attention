package daio.patientattention.manejadorEventos

import daio.patientattention.enrutamiento.Enrutador
import daio.patientattention.model.Address
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/events")
class ServicioEventos: ApplicationContextAware {

    private lateinit var enrutador: Enrutador

    override fun setApplicationContext(context: ApplicationContext) {
        this.enrutador = context.getBean(Enrutador::class.java)
    }

    @GetMapping("patients/{patient_id}/raise-alarm/{location}")
    fun raiseAlarm(@PathVariable patient_id: String, @PathVariable location: String) {
        this.enrutador.raiseAlarmState(patient_id, location)
    }

    @PostMapping("address")
    fun postAddress (@RequestBody mail: String): Address {
        return this.enrutador.postAddress(Address(mail.trim('\"')))
    }
}