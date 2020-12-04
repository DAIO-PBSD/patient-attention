package daio.patientattention.managerBD

import daio.patientattention.model.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<Address, String>