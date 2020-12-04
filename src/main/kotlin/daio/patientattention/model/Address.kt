package daio.patientattention.model

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Address (
        @Id val mail: String = ""
)