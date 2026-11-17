package com.example.modoguardian.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modoguardian.R
import com.example.modoguardian.ui.components.GuardCard

private data class SeccionTerminos(
    val titulo: String,
    val parrafos: List<String>
)

private val INTRO_TERMINOS = listOf(
    "Por favor, lea atentamente este documento antes de utilizar nuestros servicios, sitio web o aplicaciones móviles (en adelante, la “Plataforma”). Al crear una cuenta, acceder o utilizar cualquier parte de la Plataforma, usted (el “Usuario”) acepta quedar vinculado por los términos y condiciones descritos a continuación, así como por todas las políticas incorporadas por referencia.",
    "Si no está de acuerdo con la totalidad de estos términos, no debe acceder ni utilizar la Plataforma."
)

private val SECCIONES_TERMINOS = listOf(
    SeccionTerminos(
        titulo = "1. INFORMACIÓN GENERAL Y ACEPTACIÓN",
        parrafos = listOf(
            "1.1. El presente acuerdo regula el acceso y uso de los servicios prestados por Insercode (en adelante, la “Compañía”).",
            "1.2. Al hacer clic en “Acepto”, registrarse o usar la Plataforma, el Usuario declara tener al menos 18 años de edad (o la mayoría de edad legal en su jurisdicción) y contar con la capacidad legal suficiente para celebrar un contrato vinculante.",
            "1.3. Si utiliza la Plataforma en representación de una persona jurídica, usted garantiza que tiene las facultades legales para obligar a dicha entidad."
        )
    ),
    SeccionTerminos(
        titulo = "2. REGISTRO Y SEGURIDAD DE LA CUENTA",
        parrafos = listOf(
            "2.1. Creación de Cuenta: Para acceder a ciertas funciones, el Usuario debe crear una cuenta proporcionando información veraz, precisa, actualizada y completa.",
            "2.2. Confidencialidad de Credenciales: El Usuario es el único responsable de mantener la confidencialidad de su nombre de usuario, contraseña y cualquier otro mecanismo de autenticación (como tokens o factores de doble autenticación - 2FA).",
            "2.3. Uso No Autorizado: El Usuario debe notificar de inmediato a la Compañía sobre cualquier uso no autorizado de su cuenta o cualquier otra violación de seguridad detectada. La Compañía no será responsable de las pérdidas causadas por el uso no autorizado de la cuenta por parte de terceros.",
            "2.4. Responsabilidad por Actividad: El Usuario asume la responsabilidad total de todas las actividades que ocurran bajo su cuenta."
        )
    ),
    SeccionTerminos(
        titulo = "3. POLÍTICA DE PRIVACIDAD Y PROTECCIÓN DE DATOS",
        parrafos = listOf(
            "3.1. Recopilación de Datos: La Compañía recopila datos personales necesarios para brindar el servicio, tales como nombre, correo electrónico, dirección IP, datos de localización, datos de uso y detalles de facturación.",
            "3.2. Uso de la Información: Los datos recopilados se utilizan para:",
            "a) Proveer, mantener y mejorar la Plataforma.",
            "b) Procesar transacciones e identificar al Usuario.",
            "c) Enviar notificaciones de seguridad, soporte técnico y actualizaciones.",
            "d) Prevenir fraudes y garantizar la seguridad del sistema.",
            "3.3. Compartición con Terceros: No vendemos ni alquilamos sus datos personales. Solo compartiremos información con proveedores de servicios de confianza (como pasarelas de pago o proveedores de infraestructura en la nube) bajo estrictas obligaciones de confidencialidad y procesamiento.",
            "3.4. Derechos del Usuario: El Usuario tiene derecho a acceder, rectificar, cancelar, suprimir u oponerse al tratamiento de sus datos personales contactando a nuestro equipo de protección de datos."
        )
    ),
    SeccionTerminos(
        titulo = "4. POLÍTICAS Y MEDIDAS DE SEGURIDAD DE LA INFORMACIÓN",
        parrafos = listOf(
            "4.1. Cifrado y Protección: Implementamos medidas técnicas, administrativas y físicas diseñadas para proteger la información contra acceso no autorizado, pérdida, alteración o destrucción. Esto incluye cifrado de datos en tránsito (TLS/HTTPS) y en reposo (AES-256 o equivalente).",
            "4.2. Monitoreo y Auditorías: Nos reservamos el derecho de monitorear el tráfico y la actividad en la Plataforma para detectar comportamientos maliciosos, intentos de intrusión o violaciones a estos términos.",
            "4.3. Autenticación Reforzada: Para proteger la integridad de las cuentas, podemos requerir mecanismos de autenticación de múltiples factores (MFA) en determinadas acciones críticas.",
            "4.4. Notificación de Incidentes: En caso de confirmarse una brecha de seguridad que afecte los datos personales del Usuario, la Compañía notificará a los afectados y a las autoridades competentes en los plazos previstos por la legislación aplicable."
        )
    ),
    SeccionTerminos(
        titulo = "5. USO ACEPTABLE Y CONDUCTAS PROHIBIDAS",
        parrafos = listOf(
            "El Usuario se compromete a no realizar ninguna de las siguientes acciones:",
            "a) Utilizar la Plataforma para fines ilícitos o no autorizados.",
            "b) Intentar vulnerar, escanear o probar la vulnerabilidad de nuestros sistemas, redes o medidas de seguridad sin autorización expresa.",
            "c) Introducir virus, troyanos, gusanos, bombas lógicas o cualquier otro material tecnológicamente perjudicial.",
            "d) Usar técnicas de ingeniería inversa, descompilar o desmontar cualquier software de la Plataforma.",
            "e) Realizar ataques de denegación de servicio (DoS/DDoS) o interferir con el correcto funcionamiento de la red.",
            "f) Suplantar la identidad de otra persona o entidad.",
            "g) Recopilar de forma automatizada (scraping, crawling) datos de otros usuarios sin su consentimiento."
        )
    ),
    SeccionTerminos(
        titulo = "6. PROPIEDAD INTELECTUAL",
        parrafos = listOf(
            "6.1. Todos los derechos de propiedad intelectual sobre el código, diseño, textos, marcas, logotipos, gráficos y software de la Plataforma pertenecen exclusivamente a la Compañía o a sus licenciantes.",
            "6.2. Se concede al Usuario una licencia limitada, no exclusiva, no transferible y revocable para acceder y usar la Plataforma de acuerdo con estos Términos."
        )
    ),
    SeccionTerminos(
        titulo = "7. LIMITACIÓN DE RESPONSABILIDAD Y GARANTÍAS",
        parrafos = listOf(
            "7.1. Servicio “Tal Cual”: La Plataforma se proporciona “tal cual” y “según disponibilidad”, sin garantías de ningún tipo, explícitas o implícitas.",
            "7.2. Interrupciones del Servicio: La Compañía no garantiza que el acceso sea ininterrumpido, puntual, seguro o libre de errores debido a mantenimientos, fallas técnicas o causas de fuerza mayor.",
            "7.3. Límite de Daños: En la máxima medida permitida por la ley, la Compañía no será responsable por daños indirectos, incidentales, punitivos, especiales o emergentes, incluyendo pérdida de datos, lucro cesante o interrupción del negocio."
        )
    ),
    SeccionTerminos(
        titulo = "8. SUSPENSIÓN Y TERMINACIÓN",
        parrafos = listOf(
            "8.1. La Compañía se reserva el derecho de suspender o cancelar el acceso del Usuario a la Plataforma inmediatamente y sin previo aviso en caso de incumplimiento de estos Términos o por sospecha razonable de actividad fraudulenta o insegura.",
            "8.2. El Usuario puede cerrar su cuenta en cualquier momento a través de la configuración de su perfil o enviando una solicitud de soporte."
        )
    ),
    SeccionTerminos(
        titulo = "9. MODIFICACIONES A LOS TÉRMINOS",
        parrafos = listOf(
            "9.1. La Compañía puede modificar estos Términos y Políticas en cualquier momento.",
            "9.2. Las versiones actualizadas se publicarán en la Plataforma con la fecha de “Última actualización”.",
            "9.3. El uso continuado de la Plataforma tras la entrada en vigor de los cambios constituirá la aceptación de los nuevos Términos por parte del Usuario."
        )
    ),
    SeccionTerminos(
        titulo = "10. LEY APLICABLE Y JURISDICCIÓN",
        parrafos = listOf(
            "Estos Términos se regirán e interpretarán de acuerdo con las leyes del país de Chile. Cualquier disputa o controversia derivada de este acuerdo se someterá a la jurisdicción exclusiva de los tribunales de Santiago, Chile."
        )
    ),
    SeccionTerminos(
        titulo = "11. CONTACTO",
        parrafos = listOf(
            "Si tiene preguntas, comentarios o inquietudes sobre estos Términos o sobre nuestras Políticas de Seguridad y Privacidad, puede ponerse en contacto con nosotros a través de:",
            "• Correo electrónico de soporte: soporte@insercode.cl",
            "• Correo electrónico de seguridad/privacidad: privacidad@insercode.cl",
            "• Dirección postal: Santiago, Chile"
        )
    )
)

@Composable
fun TerminosScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.volver),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Column {
                Text(
                    text = stringResource(R.string.terms_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.profile_terms),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(14.dp))

        GuardCard(contentPadding = 16.dp) {
            Text(
                text = stringResource(R.string.terms_subtitle),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.terms_updated),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            INTRO_TERMINOS.forEachIndexed { index, parrafo ->
                Text(
                    text = parrafo,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (index < INTRO_TERMINOS.lastIndex) {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }

        SECCIONES_TERMINOS.forEach { seccion ->
            Spacer(Modifier.height(12.dp))
            GuardCard(contentPadding = 16.dp) {
                Text(
                    text = seccion.titulo,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                seccion.parrafos.forEachIndexed { index, parrafo ->
                    Text(
                        text = parrafo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (index < seccion.parrafos.lastIndex) {
                        Spacer(Modifier.height(6.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(26.dp))
    }
}
