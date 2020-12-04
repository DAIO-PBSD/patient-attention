package daio.patientattention.config

import daio.patientattention.enrutamiento.Enrutador
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun enrutador() = Enrutador()
}