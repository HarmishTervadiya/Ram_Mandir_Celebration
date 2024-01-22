package com.example.ram_mandir_celebration

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.ram_mandir_celebration.ui.theme.Orange
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CircularMenuScreen(applicationContext)

                }
        }

    }


    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

}


data class MenuItem(val label: String,val imageRes: Int,val index:Int=11)

@SuppressLint("SuspiciousIndentation")
@Composable
fun CircularMenu(items: List<MenuItem>, onItemSelected: (MenuItem) -> Unit) {
    var selectedItem by remember { mutableStateOf(items.first()) }

    val angleStep = 360.0 / items.size


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .rotate(180f),
            contentAlignment = Alignment.Center
        ) {
            items.forEachIndexed { index, item ->
                val angle = index * angleStep
                CircularMenuItem(
                    item = item,
                    angle = angle.toFloat(),
                    selected = item == selectedItem,
                    onClick = {
                        onItemSelected(item)
                        selectedItem = item
                    }
                )

                selectedItem.let {
                    Image(
                        painter = painterResource(id = it.imageRes),
                        contentDescription = it.label,
                        modifier = Modifier
                            .size(145.dp)
                            .graphicsLayer(rotationZ = 180f)
                            .clip(
                                CircleShape
                            ),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    }


@Composable
fun CircularMenuItem(
    item: MenuItem,
    angle: Float,
    selected: Boolean,
    onClick: () -> Unit
) {
    val radius = 300.dp


    Box(
        modifier = Modifier
            .size(radius)
            .graphicsLayer(
                translationX = (radius.value * cos(Math.toRadians(angle.toDouble()))).toFloat(),
                translationY = (radius.value * sin(Math.toRadians(angle.toDouble()))).toFloat(),
                rotationZ = 180f,
            )
            .padding(118.dp)
            .clip(CircleShape)
            .alpha(if (selected) .7f else 1f)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.label,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .shadow(elevation = 55.dp, ambientColor = Color.Black, shape = CircleShape),
            contentScale = ContentScale.FillBounds
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CircularMenuScreen(context: Context) {
    val player=ExoPlayer.Builder(context).build()
    player.setMediaItem(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/spotify-clone-red-ronin.appspot.com/o/Ram%20Mandir%20Celebration%2FSpotifyMate.com%20-%20Shree%20Ram%20Chandra%20-%20Agam%20Aggarwal.mp3?alt=media&token=176a843e-a4fd-4bb0-b59b-6e87e05df612"))
    player.volume=0.05f
    player.repeatMode=Player.REPEAT_MODE_ALL
    player.prepare()
    player.play()

    var selectedItem by remember { mutableStateOf<MenuItem?>(null) }
    val default="The Ramayana, an ancient Indian epic, narrates the life of Lord Ram, the seventh avatar of Lord Vishnu. Composed by the sage Valmiki, it spans the journey of Ram from his birth in Ayodhya to his exile, the abduction of his wife Sita by the demon king Ravana, and the epic battle that ensued. The triumph of good over evil, symbolized by Ram's victory and return to Ayodhya, is celebrated annually as Diwali. The Ramayana serves as a moral and spiritual guide, emphasizing principles of righteousness, duty, and devotion"
    val description= remember {
        mutableStateOf(listOf("Bhagwan Shree Ram son of King 'Dashrat' and Queen 'Kaushalya' from Raghukul, a central figure in Hinduism, is revered as the seventh avatar of Lord Vishnu and is considered a symbol of virtue, righteousness, and ideal kingship. The life and teachings of Lord Ram are extensively documented in the ancient Indian epic, the Ramayana, composed by the sage Valmiki. His story serves as a moral and spiritual guide for millions of people across the world, transcending geographical and cultural boundaries.",
            "Mata Sita is the Wife of Bhagwan Shree Ram, the seventh avatar of Lord Vishnu. Born from the Earth, she epitomizes purity, devotion, and resilience. Her steadfast love and unwavering support during Ram's exile and her subsequent abduction by the demon king Ravana showcase her strength and virtue. Sita's unwavering loyalty, symbolized by her Agni Pariksha (trial by fire), affirms her purity. Despite facing adversity, she emerges as a symbol of femininity, virtue, and sacrifice in the Ramayana, embodying the ideals of an ideal wife and mother. Sita's character continues to inspire devotion and reverence in the hearts of millions, reflecting the timeless values ingrained in Indian culture.",
            "Lakshman Ji is the devoted brother of Bhagwan Shree Ram, embodying loyalty and selflessness. His unwavering commitment is evident in accompanying Ram and Mata Sita during their exile. Lakshman's sacrifice, symbolized by his sleepless service and protection of the divine couple, exemplifies fraternal devotion. His heroism shines in the Ramayana's crucial battles, portraying him as an exemplary warrior and a symbol of dharma. Lakshman's legacy resonates with values of duty, honor, and sacrifice, making him an enduring symbol of brotherly love in Indian culture.",
            "King 'Dashrath' father of Shree Ram was bound to his promise to Queen 'Kaikeyi' therefore he orders his Son for 14 years of vanvaas . Despite being the rightful heir to Ayodhya's throne, he embraces humility and duty, demonstrating respect for his father's word. Mata Sita and Lakshman ji, devoted to Shree Ram, join him on this arduous journey. Shree Ram's Vanvaas symbolizes sacrifice, adherence to principles, and the triumph of righteousness over personal desires, leaving an enduring moral and spiritual legacy",
            "'Bharat' ji was son of 'Kaikeyi' and brother of Shree Ram. When he got news about Shree Ram's vanvaas he refused to sit on the throne and went to bring back Shree Ram from vanvaas but due to his promise Shree Ram refused to come back therefore 'Bharat' brought back Shree Ram's Charan Paduka (Slippers) with him and placed them on the throne and managed the rule until the return of Shree Ram. Bharat's character represents fraternal love, humility, and adherence to dharma, serving as an exemplar of familial values deeply ingrained in Indian cultural narratives. His legacy underscores the importance of righteousness and familial bonds.",
            "Ravan the 'Dashanan' (who has ten heads) was the Demon King of 'Lanka' (Golden City), he possessed immense knowledge, power, and charisma. His ten heads symbolize his multifaceted personality and intellectual prowess. He imposed as Sage (Sadhu) and kidnapped 'Mata Sita' while Shree Ram and Lakshman were away. Despite his strengths, Ravan's downfall stems from arrogance, desire, and deviation from dharma. His abduction of Mata Sita triggers the epic battle with Bhagwan Shree Ram, emphasizing the consequences of moral transgressions. While viewed as a symbol of evil, Ravan's character prompts reflection on the consequences of unchecked ego, making him a nuanced figure.",
            "Jatayu, a noble vulture who epitomizes valor and sacrifice in the Ramayan. Witnessing Mata Sita's abduction by Ravan, Jatayu courageously confronts the demon king in an attempt to rescue her. Despite his valiant efforts, Jatayu succumbs to Ravan's might. Bhagwan Shree Ram and Lakshman later find him, and with deep respect, Ram acknowledges Jatayu's sacrifice. Jatayu symbolizes selfless devotion, courage, and the timeless Indian values of protecting righteousness, making him a revered character in the cultural fabric. His sacrifice exemplifies the profound sense of duty ingrained in Indian narratives.",
            "The One Who is Know as 'Hanuman' 'Bajrangbali' 'Maruti' and many other names, He is the incarnation of Bhagwan 'Shiv' and son of 'Kesari' and 'Anjani'. He is the biggest devotee of 'Bhagwan Shree Ram' and one of the Chiranjeevi's (Immortals). He is Almighty known as Master of Asht Siddhi and Nav Niddi (Eight Super Natural Powers and Nine treasures) While on the search of the kidnapped Mata Sita Shree Ram and Lakshman meets Hanuman, From this moment Hanuman got to serve his Prabhu (Lord) and gets aware of the current situation and helps in search of Mata Sita. He is always chanting 'Jai Shree Ram' let us also chant Jai Shree Ram",
            "'Sugriva' is the virtuous Vanar king. Exiled by his brother Vali, Sugriva seeks refuge in the sacred Rishyamukha mountain. Bhagwan Shree Ram and Lakshman, in their quest to find Mata Sita helps Sugriv and Sugriva forms an alliance, promising assistance in the search for Sita. The Vanara Sena, a formidable army of monkey warriors, is assembled under Sugriva's leadership and started searching Mata Sita. Hanuman, their mighty general who had forgotten his powers due to the curse given by Matang Muni for his mischievous acts remembers his powers when King of bears 'Jamvant' tell him about his sealed powers and his curse, After that Hanuman crosses the sea, finds Mata Sita and burns Lanka, Following that Vanarsena makes Ram Setu crosses the sea reaches Lanka.",
            "Shree Ram gave many opportunities to Ravan for surrender by sending messangers like the mighty 'Angad', Despite this due to his arrogance the Battle between the Demon Army of Ravan vs The Vanarsena of Shree Ram started, Many Epic battles took place,Many Great Astra and weapons were used, Many Great Warriors died, Ravan's brother 'Kumhkaran' killed by Shree Ram and his son 'Meghanath' killed by Lakshman ji, 'Vibhishan' ( One of Chiranjeevis ) the younger brother of Ravan,stands out for his unwavering devotion to righteousness and chooses Dharma over family loyality, He seeks refuge with Bhagwan Shree Ram, offering invaluable counsel that proves instrumental in the war against Ravan. At the end the Ultimate face off between Shree Ram and Ravan took Place, After the fierce battle Ravan is defeated by Shree Ram illustrating the triumph of righteousness making this day known as Dusshera.",
            "After the fierce battle is over Shree Ram recuses Mata Sita, but Mata Sita's purity is questioned leading to 'Agni Pariksha' (trial by fire), where Mata Sita emerges unscathed, reaffirming her chastity. Bhagwan Shree Ram, accompanied by Mata Sita, Lakshman, and the loyal Vanara Sena, returns to Ayodhya after completing 14 years of vanvaas. The citizens joyously welcome them with celebration and this day is celebrated as 'Deepavli' or 'Diwali', and preparations begin for Ram's coronation. The grand coronation ceremony of Bhagwan Shree Ram as the rightful king of Ayodhya takes place, marking a moment of triumph and celebration. Bhagwan Shree Ram's reign, known as Ram Rajya, is characterized by justice, prosperity, and righteousness. It becomes an idealized era symbolizing good governance and harmony.",
            default))

    }
    val defaultDescription= listOf(
        "Bhagwan Shree Ram son of King 'Dashrat' and Queen 'Kaushalya' from Raghukul, a central figure in Hinduism, is revered as the seventh avatar of Lord Vishnu and is considered a symbol of virtue, righteousness, and ideal kingship. The life and teachings of Lord Ram are extensively documented in the ancient Indian epic, the Ramayana, composed by the sage Valmiki. His story serves as a moral and spiritual guide for millions of people across the world, transcending geographical and cultural boundaries.",
        "Mata Sita is the Wife of Bhagwan Shree Ram, the seventh avatar of Lord Vishnu. Born from the Earth, she epitomizes purity, devotion, and resilience. Her steadfast love and unwavering support during Ram's exile and her subsequent abduction by the demon king Ravana showcase her strength and virtue. Sita's unwavering loyalty, symbolized by her Agni Pariksha (trial by fire), affirms her purity. Despite facing adversity, she emerges as a symbol of femininity, virtue, and sacrifice in the Ramayana, embodying the ideals of an ideal wife and mother. Sita's character continues to inspire devotion and reverence in the hearts of millions, reflecting the timeless values ingrained in Indian culture.",
        "Lakshman Ji is the devoted brother of Bhagwan Shree Ram, embodying loyalty and selflessness. His unwavering commitment is evident in accompanying Ram and Mata Sita during their exile. Lakshman's sacrifice, symbolized by his sleepless service and protection of the divine couple, exemplifies fraternal devotion. His heroism shines in the Ramayana's crucial battles, portraying him as an exemplary warrior and a symbol of dharma. Lakshman's legacy resonates with values of duty, honor, and sacrifice, making him an enduring symbol of brotherly love in Indian culture.",
        "King 'Dashrath' father of Shree Ram was bound to his promise to Queen 'Kaikeyi' therefore he orders his Son for 14 years of vanvaas . Despite being the rightful heir to Ayodhya's throne, he embraces humility and duty, demonstrating respect for his father's word. Mata Sita and Lakshman ji, devoted to Shree Ram, join him on this arduous journey. Shree Ram's Vanvaas symbolizes sacrifice, adherence to principles, and the triumph of righteousness over personal desires, leaving an enduring moral and spiritual legacy",
        "'Bharat' ji was son of 'Kaikeyi' and brother of Shree Ram. When he got news about Shree Ram's vanvaas he refused to sit on the throne and went to bring back Shree Ram from vanvaas but due to his promise Shree Ram refused to come back therefore 'Bharat' brought back Shree Ram's Charan Paduka (Slippers) with him and placed them on the throne and managed the rule until the return of Shree Ram. Bharat's character represents fraternal love, humility, and adherence to dharma, serving as an exemplar of familial values deeply ingrained in Indian cultural narratives. His legacy underscores the importance of righteousness and familial bonds.",
        "Ravan the 'Dashanan' (who has ten heads) was the Demon King of 'Lanka' (Golden City), he possessed immense knowledge, power, and charisma. His ten heads symbolize his multifaceted personality and intellectual prowess. He imposed as Sage (Sadhu) and kidnapped 'Mata Sita' while Shree Ram and Lakshman were away. Despite his strengths, Ravan's downfall stems from arrogance, desire, and deviation from dharma. His abduction of Mata Sita triggers the epic battle with Bhagwan Shree Ram, emphasizing the consequences of moral transgressions. While viewed as a symbol of evil, Ravan's character prompts reflection on the consequences of unchecked ego, making him a nuanced figure.",
        "Jatayu, a noble vulture who epitomizes valor and sacrifice in the Ramayan. Witnessing Mata Sita's abduction by Ravan, Jatayu courageously confronts the demon king in an attempt to rescue her. Despite his valiant efforts, Jatayu succumbs to Ravan's might. Bhagwan Shree Ram and Lakshman later find him, and with deep respect, Ram acknowledges Jatayu's sacrifice. Jatayu symbolizes selfless devotion, courage, and the timeless Indian values of protecting righteousness, making him a revered character in the cultural fabric. His sacrifice exemplifies the profound sense of duty ingrained in Indian narratives.",
        "The One Who is Know as 'Hanuman' 'Bajrangbali' 'Maruti' and many other names, He is the incarnation of Bhagwan 'Shiv' and son of 'Kesari' and 'Anjani'. He is the biggest devotee of 'Bhagwan Shree Ram' and one of the Chiranjeevi's (Immortals). He is Almighty known as Master of Asht Siddhi and Nav Niddi (Eight Super Natural Powers and Nine treasures) While on the search of the kidnapped Mata Sita Shree Ram and Lakshman meets Hanuman, From this moment Hanuman got to serve his Prabhu (Lord) and gets aware of the current situation and helps in search of Mata Sita. He is always chanting 'Jai Shree Ram' let us also chant Jai Shree Ram",
        "'Sugriva' is the virtuous Vanar king. Exiled by his brother Vali, Sugriva seeks refuge in the sacred Rishyamukha mountain. Bhagwan Shree Ram and Lakshman, in their quest to find Mata Sita helps Sugriv and Sugriva forms an alliance, promising assistance in the search for Sita. The Vanara Sena, a formidable army of monkey warriors, is assembled under Sugriva's leadership and started searching Mata Sita. Hanuman, their mighty general who had forgotten his powers due to the curse given by Matang Muni for his mischievous acts remembers his powers when King of bears 'Jamvant' tell him about his sealed powers and his curse, After that Hanuman crosses the sea, finds Mata Sita and burns Lanka, Following that Vanarsena makes Ram Setu crosses the sea reaches Lanka.",
        "Shree Ram gave many opportunities to Ravan for surrender by sending messangers like the mighty 'Angad', Despite this due to his arrogance the Battle between the Demon Army of Ravan vs The Vanarsena of Shree Ram started, Many Epic battles took place,Many Great Astra and weapons were used, Many Great Warriors died, Ravan's brother 'Kumhkaran' killed by Shree Ram and his son 'Meghanath' killed by Lakshman ji, 'Vibhishan' ( One of Chiranjeevis ) the younger brother of Ravan,stands out for his unwavering devotion to righteousness and chooses Dharma over family loyality, He seeks refuge with Bhagwan Shree Ram, offering invaluable counsel that proves instrumental in the war against Ravan. At the end the Ultimate face off between Shree Ram and Ravan took Place, After the fierce battle Ravan is defeated by Shree Ram illustrating the triumph of righteousness making this day known as Dusshera.",
        "After the fierce battle is over Shree Ram recuses Mata Sita, but Mata Sita's purity is questioned leading to 'Agni Pariksha' (trial by fire), where Mata Sita emerges unscathed, reaffirming her chastity. Bhagwan Shree Ram, accompanied by Mata Sita, Lakshman, and the loyal Vanara Sena, returns to Ayodhya after completing 14 years of vanvaas. The citizens joyously welcome them with celebration and this day is celebrated as 'Deepavli' or 'Diwali', and preparations begin for Ram's coronation. The grand coronation ceremony of Bhagwan Shree Ram as the rightful king of Ayodhya takes place, marking a moment of triumph and celebration. Bhagwan Shree Ram's reign, known as Ram Rajya, is characterized by justice, prosperity, and righteousness. It becomes an idealized era symbolizing good governance and harmony."
    ,default
    )

    val gujaratiDescription= listOf(
        "રઘુકુલના રાજા 'દશરત' અને રાણી 'કૌશલ્યા'ના પુત્ર ભગવાન શ્રીરામ, જે હિન્દુ ધર્મમાં કેન્દ્રિય વ્યક્તિ છે, તે ભગવાન વિષ્ણુના સાતમા અવતાર તરીકે આદરવામાં આવે છે અને તેમને સદ્ગુણ, સદાચાર અને આદર્શ રાજાશાહીનું પ્રતીક માનવામાં આવે છે. ઋષિ વાલ્મીકિ દ્વારા રચિત પ્રાચીન ભારતીય મહાકાવ્ય રામાયણમાં ભગવાન રામના ઉપદેશોનું વ્યાપકપણે દસ્તાવેજીકરણ કરવામાં આવ્યું છે. તેમની વાર્તા ભૌગોલિક અને સાંસ્કૃતિક સીમાઓને પાર કરીને વિશ્વભરના લાખો લોકો માટે નૈતિક અને આધ્યાત્મિક માર્ગદર્શક તરીકે સેવા આપે છે.",
        "માતા સીતા ભગવાન વિષ્ણુના સાતમા અવતાર, ભગવાન શ્રી રામની પત્ની છે. પૃથ્વી પરથી જન્મેલી, તે શુદ્ધતા, ભક્તિ અને સ્થિતિસ્થાપકતાનું પ્રતીક છે. રામના વનવાસ દરમિયાન અને તેના પછી રાક્ષસ રાજા રાવણ દ્વારા અપહરણ દરમિયાન તેમનો અડગ પ્રેમ અને અતૂટ ટેકો. તેણીની શક્તિ અને સદ્ગુણ દર્શાવો. સીતાની અતૂટ વફાદારી, તેણીની અગ્નિ પરિક્ષા (અગ્નિ દ્વારા અજમાયશ) દ્વારા પ્રતીકિત, તેણીની શુદ્ધતાની પુષ્ટિ કરે છે. પ્રતિકૂળતાનો સામનો કરવા છતાં, તે રામાયણમાં સ્ત્રીત્વ, સદ્ગુણ અને બલિદાનના પ્રતીક તરીકે ઉભરી આવે છે, જે આદર્શોને મૂર્ત બનાવે છે. આદર્શ પત્ની અને માતા. સીતાનું પાત્ર લાખો લોકોના હૃદયમાં ભક્તિ અને આદરને પ્રેરિત કરતું રહે છે, જે ભારતીય સંસ્કૃતિમાં સમાવિષ્ટ કાલાતીત મૂલ્યોને પ્રતિબિંબિત કરે છે.",
        "લક્ષ્મણજી ભગવાન શ્રી રામના સમર્પિત ભાઈ છે, જે વફાદારી અને નિઃસ્વાર્થતાને મૂર્તિમંત કરે છે. રામ અને માતા સીતાના વનવાસ દરમિયાન તેમની અતૂટ પ્રતિબદ્ધતા સ્પષ્ટ છે. લક્ષ્મણનું બલિદાન, તેમની નિદ્રાહીન સેવા અને દૈવી દંપતીના રક્ષણનું પ્રતીક છે, તે ભાઈચારાની ભક્તિનું ઉદાહરણ છે. તેમની વીરતા રામાયણની નિર્ણાયક લડાઈઓમાં ચમકે છે, તેમને એક અનુકરણીય યોદ્ધા અને ધર્મના પ્રતીક તરીકે દર્શાવવામાં આવ્યા છે. લક્ષ્મણનો વારસો ફરજ, સન્માન અને બલિદાનના મૂલ્યો સાથે પડઘો પાડે છે, જે તેમને ભારતીય સંસ્કૃતિમાં ભાઈચારાના પ્રેમનું કાયમી પ્રતીક બનાવે છે.",
        "શ્રી રામના પિતા રાજા 'દશરથ' રાણી 'કૈકેયી'ને આપેલા વચનમાં બંધાયેલા હતા તેથી તેઓ તેમના પુત્રને 14 વર્ષ માટે વનવાસનો આદેશ આપે છે. અયોધ્યાની ગાદીના યોગ્ય વારસદાર હોવા છતાં, તે નમ્રતા અને ફરજને અપનાવે છે, તેના પિતા પ્રત્યે આદર દર્શાવે છે. માતા સીતા અને લક્ષ્મણજી, શ્રી રામને સમર્પિત, આ મુશ્કેલ પ્રવાસમાં તેમની સાથે જોડાઓ. શ્રી રામનો વનવાસ બલિદાન, સિદ્ધાંતોનું પાલન, અને વ્યક્તિગત ઇચ્છાઓ પર સદાચારની જીતનું પ્રતીક છે, જે કાયમી નૈતિક અને આધ્યાત્મિક વારસો છોડીને જાય છે",
        "ભારતજી રાણી 'કૈકેયી'ના પુત્ર અને શ્રીરામના ભાઈ હતા. જ્યારે તેમને શ્રીરામના વનવાસના સમાચાર મળ્યા ત્યારે તેમણે સિંહાસન પર બેસવાની ના પાડી અને શ્રીરામને વનવાસમાંથી પાછા લાવવા ગયા પરંતુ તેમના વચનને કારણે શ્રીરામે ના પાડી. પાછા આવવા માટે 'ભારત' પોતાની સાથે શ્રી રામની ચરણ પાદુકા (ચપ્પલ) પાછી લાવ્યો અને તેમને સિંહાસન પર બેસાડ્યો અને શ્રી રામના પાછા ન આવે ત્યાં સુધી શાસનનું સંચાલન કર્યું. ભરતનું પાત્ર ભાઈચારો, નમ્રતા અને ધર્મના પાલનનું પ્રતિનિધિત્વ કરે છે. ભારતીય સાંસ્કૃતિક કથાઓમાં ઊંડે ઊંડે જડેલા કૌટુંબિક મૂલ્યોનું ઉદાહરણ. તેમનો વારસો સચ્ચાઈ અને કૌટુંબિક બંધનોના મહત્વ પર ભાર મૂકે છે.",
        "રાવણ 'દશાનન' (જેના દસ માથા છે) 'લંકા' (ગોલ્ડન સિટી) ના રાક્ષસ રાજા હતા, તેમની પાસે પુષ્કળ જ્ઞાન, શક્તિ અને કરિશ્મા હતી. તેમના દસ માથા તેમના બહુમુખી વ્યક્તિત્વ અને બૌદ્ધિક પરાક્રમનું પ્રતીક છે. તેમણે ઋષિ તરીકે લાદ્યો (સાધુ) અને શ્રી રામ અને લક્ષ્મણ દૂર હતા ત્યારે 'માતા સીતા'નું અપહરણ કર્યું. તેની શક્તિઓ હોવા છતાં, રાવણનું પતન ઘમંડ, ઈચ્છા અને ધર્મથી વિચલનને કારણે થાય છે. માતા સીતાનું તેનું અપહરણ ભગવાન શ્રી રામ સાથે મહાકાવ્ય યુદ્ધ શરૂ કરે છે, જે તેના પર ભાર મૂકે છે. નૈતિક ઉલ્લંઘનના પરિણામો. દુષ્ટતાના પ્રતીક તરીકે જોવામાં આવે ત્યારે, રાવણનું પાત્ર અનિયંત્રિત અહંકારના પરિણામો પર પ્રતિબિંબિત કરે છે, જે તેને એક સૂક્ષ્મ વ્યક્તિ બનાવે છે.",
        "જટાયુ, એક ઉમદા ગીધ જે રામાયણમાં બહાદુરી અને બલિદાનનું પ્રતીક છે. રાવણ દ્વારા માતા સીતાના અપહરણની સાક્ષી આપતા, જટાયુએ તેને બચાવવાના પ્રયાસમાં રાક્ષસ રાજાનો હિંમતભેર સામનો કર્યો. તેના બહાદુરી પ્રયાસો છતાં, જટાયુ રાવણની શક્તિનો ભોગ બને છે. ભગવાન શ્રી રામ અને ભગવાન શ્રી રામ. લક્ષ્મણ પાછળથી તેને શોધે છે, અને ઊંડા આદર સાથે, રામ જટાયુના બલિદાનને સ્વીકારે છે. જટાયુ નિઃસ્વાર્થ નિષ્ઠા, હિંમત અને સદાચારનું રક્ષણ કરવાના કાલાતીત ભારતીય મૂલ્યોનું પ્રતીક છે, જે તેને સાંસ્કૃતિક માળખામાં એક આદરણીય પાત્ર બનાવે છે. તેનું બલિદાન ફરજની ગહન ભાવનાનું ઉદાહરણ આપે છે. ભારતીય કથાઓમાં.",
        "જે 'હનુમાન' 'બજરંગબલી' 'મારુતિ' અને બીજા અનેક નામોથી ઓળખાય છે, તે ભગવાન 'શિવ'નો અવતાર અને 'કેસરી' અને 'અંજની'ના પુત્ર છે. તે 'ભગવાન શ્રી'ના સૌથી મોટા ભક્ત છે. રામ' અને ચિરંજીવીઓમાંથી એક (અમર) તે સર્વશક્તિમાન છે જે અષ્ટ સિદ્ધિ અને નવ નિદ્દી (આઠ સુપર નેચરલ પાવર્સ અને નવ ખજાના)ના સ્વામી તરીકે ઓળખાય છે, જ્યારે અપહરણ કરાયેલી માતા સીતા શ્રી રામ અને લક્ષ્મણ હનુમાનને મળે છે. હનુમાનને તેમના પ્રભુ (ભગવાન)ની સેવા કરવાની ક્ષણ મળી અને વર્તમાન પરિસ્થિતિથી વાકેફ થયા અને માતા સીતાની શોધમાં મદદ કરે છે. તે હંમેશા 'જય શ્રી રામ'નો જપ કરે છે, ચાલો આપણે પણ જય શ્રી રામનો જપ કરીએ",
        "'સુગ્રીવ' એ સદ્ગુણી વાનર રાજા છે. તેના ભાઈ વાલી દ્વારા દેશનિકાલ કરીને, સુગ્રીવ પવિત્ર ઋષ્યમુખ પર્વતમાં આશ્રય લે છે. ભગવાન શ્રી રામ અને લક્ષ્મણ, માતા સીતાને શોધવાની તેમની શોધમાં સુગ્રીવ અને સુગ્રીવને જોડાણ રચવામાં મદદ કરે છે, આશાસ્પદ સહાયતા સીતાની શોધ કરો. વાનર સેના, વાનર યોદ્ધાઓની એક પ્રચંડ સેના, સુગ્રીવના નેતૃત્વમાં એકત્ર થઈ અને માતા સીતાને શોધવાનું શરૂ કર્યું. હનુમાન, તેમના શક્તિશાળી સેનાપતિ કે જેઓ તેમના તોફાની કૃત્યો માટે માતંગ મુનિ દ્વારા આપવામાં આવેલા શ્રાપને કારણે તેમની શક્તિઓ ભૂલી ગયા હતા. શક્તિઓ જ્યારે રીંછનો રાજા 'જમવંત' તેને તેની સીલબંધ શક્તિઓ અને તેના શ્રાપ વિશે કહે છે, તે પછી હનુમાન સમુદ્ર પાર કરે છે, માતા સીતાને શોધે છે અને લંકા બાળી નાખે છે, તે પછી વાનરસેના રામ સેતુને સમુદ્ર પાર કરીને લંકા પહોંચે છે.",
        "શ્રીરામે પરાક્રમી 'અંગદ' જેવા સંદેશવાહકો મોકલીને રાવણને શરણાગતિની ઘણી તકો આપી, તેમ છતાં તેમના ઘમંડને કારણે રાવણની રાક્ષસ સેના અને શ્રી રામની વાનરસેના વચ્ચે યુદ્ધ શરૂ થયું, ઘણી મહાકાવ્ય યુદ્ધો થયા, ઘણા મહાન અસ્ત્રો અને શસ્ત્રોનો ઉપયોગ કરવામાં આવ્યો હતો, ઘણા મહાન યોદ્ધાઓ મૃત્યુ પામ્યા હતા, રાવણના ભાઈ 'કુમ્હકરણ'ને શ્રી રામ દ્વારા માર્યા ગયા હતા અને તેમના પુત્ર 'મેઘનાથ'ને લક્ષ્મણજી દ્વારા માર્યા ગયા હતા, રાવણના નાના ભાઈ 'વિભીષણ' (ચિરંજીવીઓમાંના એક) તેમના અવિચારી માટે બહાર આવે છે. પ્રામાણિકતા પ્રત્યેની ભક્તિ અને પારિવારિક વફાદારી કરતાં ધર્મ પસંદ કરે છે, તે ભગવાન શ્રી રામનો આશ્રય લે છે, રાવણ સામેના યુદ્ધમાં નિમિત્ત સાબિત થતી અમૂલ્ય સલાહ આપે છે. અંતે શ્રીરામ અને રાવણ વચ્ચે અંતિમ મુકાબલો થયો, ભીષણ યુદ્ધ પછી રાવણ શ્રી રામ દ્વારા આ દિવસને દશેરા તરીકે ઓળખવામાં આવતા ન્યાયીપણાની જીતનું ચિત્રણ કરીને પરાજિત થાય છે.",
        "ભીષણ યુદ્ધ સમાપ્ત થયા પછી, શ્રી રામે માતા સીતાને છોડી દીધા, પરંતુ માતા સીતાની શુદ્ધતા પર પ્રશ્ન ઉઠાવવામાં આવે છે જે 'અગ્નિ પરિક્ષા' (અગ્નિ દ્વારા અજમાયશ) તરફ દોરી જાય છે, જ્યાં માતા સીતા સહીસલામત બહાર આવે છે, તેની પવિત્રતાની પુષ્ટિ કરે છે. ભગવાન શ્રી રામ, માતા સીતાની સાથે, લક્ષ્મણ, અને વફાદાર વનરા સેના, વનવાસના 14 વર્ષ પૂરા કરીને અયોધ્યા પાછા ફર્યા. નાગરિકો આનંદપૂર્વક તેમનું સ્વાગત કરે છે અને આ દિવસને 'દીપાવલી' અથવા 'દિવાળી' તરીકે ઉજવવામાં આવે છે, અને રામના રાજ્યાભિષેકની તૈયારીઓ શરૂ થાય છે. ભવ્ય રાજ્યાભિષેક સમારોહ અયોધ્યાના હકદાર રાજા તરીકે ભગવાન શ્રી રામની ઉજવણી થાય છે, જે વિજય અને ઉજવણીની એક ક્ષણને ચિહ્નિત કરે છે. ભગવાન શ્રી રામનું શાસન, જે રામ રાજ્ય તરીકે ઓળખાય છે, તે ન્યાય, સમૃદ્ધિ અને સદાચાર દ્વારા વર્ગીકૃત થયેલ છે. તે એક આદર્શ યુગ બની જાય છે જે સુશાસનનું પ્રતીક છે અને સંવાદિતા."
        ,"રામાયણ, એક પ્રાચીન ભારતીય મહાકાવ્ય, ભગવાન વિષ્ણુના સાતમા અવતાર, ભગવાન રામના જીવનનું વર્ણન કરે છે. ઋષિ વાલ્મીકિ દ્વારા રચિત, તે રામના અયોધ્યામાં જન્મથી તેમના વનવાસ સુધી, તેમની પત્ની સીતાના અપહરણ સુધીની સફરને આવરી લે છે. રાક્ષસ રાજા રાવણ દ્વારા, અને મહાકાવ્ય યુદ્ધ કે જેનું પરિણામ આવ્યું. દુષ્ટતા પર સારાની જીત, રામની જીત અને અયોધ્યા પાછા ફરવાનું પ્રતીક છે, તે વાર્ષિક દિવાળી તરીકે ઉજવવામાં આવે છે. રામાયણ નૈતિક અને આધ્યાત્મિક માર્ગદર્શક તરીકે સેવા આપે છે, સચ્ચાઈના સિદ્ધાંતો પર ભાર મૂકે છે, ફરજ, અને ભક્તિ")
    val hindiDescription= listOf(
        "रघुकुल के राजा 'दशरथ' और रानी 'कौशल्या' के पुत्र भगवान श्री राम, हिंदू धर्म में एक केंद्रीय व्यक्ति हैं, जिन्हें भगवान विष्णु के सातवें अवतार के रूप में सम्मानित किया जाता है और उन्हें सदाचार, धार्मिकता और आदर्श राजत्व का प्रतीक माना जाता है। जीवन और भगवान राम की शिक्षाओं को ऋषि वाल्मिकी द्वारा रचित प्राचीन भारतीय महाकाव्य, रामायण में बड़े पैमाने पर प्रलेखित किया गया है। उनकी कहानी भौगोलिक और सांस्कृतिक सीमाओं से परे, दुनिया भर के लाखों लोगों के लिए एक नैतिक और आध्यात्मिक मार्गदर्शक के रूप में कार्य करती है।",
        "माता सीता भगवान विष्णु के सातवें अवतार भगवान श्री राम की पत्नी हैं। पृथ्वी से जन्मी, वह पवित्रता, भक्ति और लचीलेपन का प्रतीक हैं। राम के वनवास के दौरान उनका दृढ़ प्रेम और अटूट समर्थन और राक्षस राजा रावण द्वारा उनका अपहरण अपनी शक्ति और सद्गुण का प्रदर्शन करें। सीता की अटूट निष्ठा, जो उनकी अग्नि परीक्षा (अग्नि द्वारा परीक्षण) का प्रतीक है, उनकी पवित्रता की पुष्टि करती है। विपरीत परिस्थितियों का सामना करने के बावजूद, वह रामायण में स्त्रीत्व, सदाचार और बलिदान के प्रतीक के रूप में उभरती हैं, एक आदर्श के रूप में आदर्श पत्नी और माँ। सीता का चरित्र आज भी लाखों लोगों के दिलों में भक्ति और श्रद्धा को प्रेरित करता है, जो भारतीय संस्कृति में निहित शाश्वत मूल्यों को दर्शाता है।",
        "लक्ष्मण जी भगवान श्री राम के समर्पित भाई हैं, जो निष्ठा और निस्वार्थता के प्रतीक हैं। उनकी अटूट प्रतिबद्धता उनके वनवास के दौरान राम और माता सीता के साथ रहने में स्पष्ट है। लक्ष्मण का बलिदान, उनकी निद्रालु सेवा और दिव्य जोड़े की सुरक्षा का प्रतीक है, भाईचारे की भक्ति का उदाहरण है . उनकी वीरता रामायण के महत्वपूर्ण युद्धों में चमकती है, जो उन्हें एक अनुकरणीय योद्धा और धर्म के प्रतीक के रूप में चित्रित करती है। लक्ष्मण की विरासत कर्तव्य, सम्मान और बलिदान के मूल्यों के साथ गूंजती है, जो उन्हें भारतीय संस्कृति में भाईचारे के प्यार का एक स्थायी प्रतीक बनाती है।",
        "श्री राम के पिता राजा 'दशरथ' रानी 'कैकेयी' को दिए गए अपने वादे से बंधे थे, इसलिए उन्होंने अपने पुत्र को 14 साल के लिए वनवास का आदेश दिया। अयोध्या के सिंहासन के असली उत्तराधिकारी होने के बावजूद, उन्होंने अपने पिता के प्रति सम्मान प्रदर्शित करते हुए विनम्रता और कर्तव्य को अपनाया। शब्द। श्री राम के प्रति समर्पित माता सीता और लक्ष्मण जी, इस कठिन यात्रा में उनके साथ हैं। श्री राम का वनवास त्याग, सिद्धांतों के पालन और व्यक्तिगत इच्छाओं पर धार्मिकता की विजय का प्रतीक है, जो एक स्थायी नैतिक और आध्यात्मिक विरासत छोड़ता है",
        "'भरत' जी 'कैकेयी' के पुत्र और श्री राम के भाई थे। जब उन्हें श्री राम के वनवास के बारे में खबर मिली तो उन्होंने सिंहासन पर बैठने से इनकार कर दिया और श्री राम को वनवास से वापस लाने के लिए गए लेकिन अपने वचन के कारण श्री राम ने ऐसा करने से इनकार कर दिया। इसलिए वापस आएं 'भरत' श्री राम की चरण पादुका (चप्पल) अपने साथ वापस ले आए और उन्हें सिंहासन पर रख दिया और श्री राम की वापसी तक शासन संभाला। भरत का चरित्र भ्रातृ प्रेम, विनम्रता और धर्म के पालन का प्रतिनिधित्व करता है, एक के रूप में सेवा करता है भारतीय सांस्कृतिक आख्यानों में गहराई से समाहित पारिवारिक मूल्यों का उदाहरण। उनकी विरासत धार्मिकता और पारिवारिक बंधनों के महत्व को रेखांकित करती है।",
        "रावण 'दशानन' (जिसके दस सिर थे) लंका (गोल्डन सिटी) का दानव राजा था, उसके पास अपार ज्ञान, शक्ति और करिश्मा था। उसके दस सिर उसके बहुमुखी व्यक्तित्व और बौद्धिक कौशल का प्रतीक हैं। उसने ऋषि के रूप में स्थापित किया (साधु) और 'माता सीता' का अपहरण कर लिया, जबकि श्री राम और लक्ष्मण दूर थे। अपनी शक्तियों के बावजूद, रावण का पतन अहंकार, इच्छा और धर्म से विचलन के कारण हुआ। माता सीता का उसका अपहरण भगवान श्री राम के साथ महाकाव्य युद्ध को ट्रिगर करता है, इस बात पर जोर दिया गया नैतिक अपराधों के परिणाम। जबकि रावण का चरित्र बुराई के प्रतीक के रूप में देखा जाता है, अनियंत्रित अहंकार के परिणामों पर प्रतिबिंब को प्रेरित करता है, जिससे वह एक सूक्ष्म व्यक्ति बन जाता है।",
        "जटायु, एक महान गिद्ध, जो रामायण में वीरता और बलिदान का प्रतीक है। रावण द्वारा माता सीता के अपहरण को देखकर, जटायु ने उन्हें बचाने के प्रयास में साहसपूर्वक राक्षस राजा का सामना किया। अपने बहादुर प्रयासों के बावजूद, जटायु ने रावण की ताकत के आगे घुटने टेक दिए। भगवान श्री राम और बाद में लक्ष्मण ने उसे ढूंढ लिया, और गहरे सम्मान के साथ, राम ने जटायु के बलिदान को स्वीकार किया। जटायु निस्वार्थ भक्ति, साहस और धार्मिकता की रक्षा के शाश्वत भारतीय मूल्यों का प्रतीक है, जो उसे सांस्कृतिक ताने-बाने में एक श्रद्धेय चरित्र बनाता है। उसका बलिदान निहित कर्तव्य की गहरी भावना का उदाहरण है भारतीय आख्यानों में।",
        "जिन्हें 'हनुमान' 'बजरंगबली' 'मारुति' और कई अन्य नामों से जाना जाता है, वह भगवान 'शिव' के अवतार और 'केसरी' और 'अंजनी' के पुत्र हैं। वह 'भगवान श्री' के सबसे बड़े भक्त हैं राम' और चिरंजीवी (अमर) में से एक। वह सर्वशक्तिमान हैं जिन्हें अष्ट सिद्धि और नव निधि (आठ सुपर प्राकृतिक शक्तियां और नौ खजाने) के स्वामी के रूप में जाना जाता है। अपहृत माता सीता की खोज के दौरान श्री राम और लक्ष्मण की हनुमान से मुलाकात होती है। जिस क्षण हनुमान को अपने प्रभु (भगवान) की सेवा करने का मौका मिला और वे वर्तमान स्थिति से अवगत हो गए और माता सीता की खोज में मदद की। वह हमेशा 'जय श्री राम' का जाप करते हैं, आइए हम भी जय श्री राम का जाप करें।'",
        "'सुग्रीव' पुण्य वानर राजा है। अपने भाई बाली द्वारा निर्वासित, सुग्रीव पवित्र ऋष्यमुख पर्वत में शरण लेता है। भगवान श्री राम और लक्ष्मण, माता सीता को खोजने की अपनी खोज में सुग्रीव की मदद करते हैं और सुग्रीव एक गठबंधन बनाते हैं, सहायता का वादा करते हैं सीता की खोज। वानर सेना, वानर योद्धाओं की एक दुर्जेय सेना, सुग्रीव के नेतृत्व में इकट्ठी हुई और माता सीता की खोज शुरू की। हनुमान, उनके शक्तिशाली सेनापति जो अपने शरारती कृत्यों के लिए मतंग मुनि द्वारा दिए गए श्राप के कारण अपनी शक्तियों को भूल गए थे, उन्हें उनकी याद आती है शक्तियां जब भालू के राजा 'जामवंत' ने उन्हें अपनी सीलबंद शक्तियों और अपने श्राप के बारे में बताया, उसके बाद हनुमान ने समुद्र पार किया, माता सीता को ढूंढा और लंका को जला दिया, जिसके बाद वानरसेना ने राम सेतु बनाया और समुद्र पार कर लंका पहुंची।",
        "श्री राम ने शक्तिशाली 'अंगद' जैसे दूतों को भेजकर रावण को आत्मसमर्पण के कई अवसर दिए, इसके बावजूद उसके अहंकार के कारण रावण की राक्षसी सेना बनाम श्री राम की वानरसेना के बीच लड़ाई शुरू हुई, कई महाकाव्य युद्ध हुए, कई महान अस्त्र और हथियारों का इस्तेमाल किया गया, कई महान योद्धा मारे गए, रावण के भाई 'कुम्हकर्ण' को श्री राम ने मार डाला और उसके बेटे 'मेघनाथ' को लक्ष्मण जी ने मार डाला, रावण का छोटा भाई 'विभीषण' (चिरंजीवियों में से एक) अपनी अटलता के लिए खड़ा था धार्मिकता के प्रति समर्पण और पारिवारिक वफादारी के बजाय धर्म को चुनना, वह भगवान श्री राम की शरण लेता है, अमूल्य सलाह देता है जो रावण के खिलाफ युद्ध में महत्वपूर्ण साबित होती है। अंत में श्री राम और रावण के बीच अंतिम आमना-सामना हुआ, भयंकर युद्ध के बाद रावण श्री राम द्वारा पराजित होने पर यह दिन धर्म की विजय को दर्शाता है, जिसे दशहरा के रूप में जाना जाता है।",
        "भयंकर युद्ध समाप्त होने के बाद श्री राम ने माता सीता को त्याग दिया, लेकिन माता सीता की पवित्रता पर सवाल उठाया गया, जिसके कारण 'अग्नि परीक्षा' (अग्नि द्वारा परीक्षण) हुई, जहां माता सीता बेदाग निकलीं और अपनी पवित्रता की पुष्टि की। भगवान श्री राम, माता सीता के साथ, लक्ष्मण, और वफादार वानर सेना, वनवास के 14 साल पूरे करने के बाद अयोध्या लौटते हैं। नागरिक खुशी से जश्न मनाते हुए उनका स्वागत करते हैं और इस दिन को 'दीपावली' या 'दिवाली' के रूप में मनाया जाता है, और राम के राज्याभिषेक की तैयारी शुरू होती है। भव्य राज्याभिषेक समारोह भगवान श्री राम को अयोध्या के असली राजा के रूप में स्थापित किया जाना विजय और उत्सव का क्षण है। भगवान श्री राम का शासनकाल, जिसे राम राज्य के रूप में जाना जाता है, न्याय, समृद्धि और धार्मिकता की विशेषता है। यह सुशासन और सुशासन का प्रतीक एक आदर्श युग बन गया है। सद्भाव।"
            ,"रामायण, एक प्राचीन भारतीय महाकाव्य, भगवान विष्णु के सातवें अवतार, भगवान राम के जीवन का वर्णन करता है। ऋषि वाल्मिकी द्वारा रचित, यह राम की अयोध्या में उनके जन्म से लेकर उनके वनवास, उनकी पत्नी सीता के अपहरण तक की यात्रा का वर्णन करता है। राक्षस राजा रावण द्वारा, और उसके बाद हुआ महाकाव्य युद्ध। बुराई पर अच्छाई की विजय, राम की जीत और अयोध्या वापसी का प्रतीक, हर साल दिवाली के रूप में मनाया जाता है। रामायण एक नैतिक और आध्यात्मिक मार्गदर्शक के रूप में कार्य करता है, जो धार्मिकता के सिद्धांतों पर जोर देता है, कर्तव्य, और भक्ति")


    Scaffold(
        topBar = {  },
        content = {
            Spacer(modifier = Modifier.padding(it))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, it.calculateTopPadding(), 0.dp, 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(painter = painterResource(id = R.drawable.rammandir), contentScale = ContentScale.FillBounds, contentDescription ="Bhagwan Shree Ram", modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp))

                FlowRow(verticalArrangement = Arrangement.Center, horizontalArrangement = Arrangement.Absolute.SpaceAround, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)) {
                    ElevatedButton(onClick = {
                        if (player.isPlaying) {
                            player.pause()
                        }
//                        player.volume=0.5f
                        player.clearMediaItems()
                        player.setMediaItem(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/spotify-clone-red-ronin.appspot.com/o/Ram%20Mandir%20Celebration%2FSpotifyMate.com%20-%20Hum%20Katha%20Sunaate%20Ram%20Sakal%20Gun%20Dhaam%20Ki%20-%20Ravindra%20Jain.mp3?alt=media&token=4091e770-3c70-482d-9bdd-4329f0092ebb"))
                        player.play()


                    }, colors = ButtonDefaults.elevatedButtonColors(containerColor = Orange)) {
                        Text(text ="Play Ramayan", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black,)
                    }

                    ElevatedButton(onClick = {
                        if (player.isPlaying) {
                            player.pause()
                        }
//                        player.volume=0.7f
                        player.clearMediaItems()
                        player.setMediaItem(MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/spotify-clone-red-ronin.appspot.com/o/Ram%20Mandir%20Celebration%2FSpotifyMate.com%20-%20Shree%20Ram%20Chandra%20-%20Agam%20Aggarwal.mp3?alt=media&token=176a843e-a4fd-4bb0-b59b-6e87e05df612"))
                        player.play()
                    }, shape = CircleShape, colors = ButtonDefaults.elevatedButtonColors(contentColor = Color.Black, containerColor = Orange))
                    {
                        Text(text ="Play Ram Stuti", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                    }
                }

//                Spacer(modifier = Modifier.height(5.dp))
                CircularMenu(
                    items = listOf(
                        MenuItem("Bhagwan Shree Ram",R.drawable.bhagwaan_shree_ram,0),
                        MenuItem("Mata Sita", R.drawable.matasita,1),
                        MenuItem("Lakshman Ji", R.drawable.lakshman_ji,2),
                        MenuItem("Vangaman", R.drawable.vangaman,3),
                        MenuItem("Bharat ji", R.drawable.bharat,4),
                        MenuItem("Ravan", R.drawable.ravan,5),
                        MenuItem("Jatayu", R.drawable.jatayu,6),
                        MenuItem("Hanuman", R.drawable.bajrangbali,7),
                        MenuItem("Vanar Sena", R.drawable.vanarsena,8),
                        MenuItem("Yudh of Ramayan", R.drawable.ramayan_yudh,9),
                        MenuItem("Ayodhya Punar Agaman", R.drawable.punaragman,10),

                    )
                ) { item ->
                    selectedItem = item
                }

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedCard(modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState()),
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color.White,
                        containerColor = Orange
                    )) {

                    FlowRow(verticalArrangement = Arrangement.Center, horizontalArrangement = Arrangement.Absolute.SpaceAround, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 3.dp)) {
                        ElevatedButton(onClick = {
                            description.value=gujaratiDescription
                        }) {
                            Text(text ="Gujarati", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black, modifier =Modifier.width(55.dp))
                        }

                        Text(text =selectedItem?.label ?: "Ramayan", fontWeight = FontWeight.Bold, fontSize = 25.sp, textAlign = TextAlign.Center, maxLines = 3, modifier = Modifier
                            .padding(3.dp)
                            .weight(.4f)
                            .clickable {
                                description.value = defaultDescription
                            })

                        ElevatedButton(onClick = {
                            description.value=hindiDescription

                        }, shape = CircleShape, colors = ButtonDefaults.elevatedButtonColors(contentColor = Color.Black))
                        {
                            Text(text ="Hindi", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,modifier =Modifier.width(55.dp))

                        }
                    }


                    Text(text =description.value[selectedItem?.index ?:11], fontWeight = FontWeight.Medium, fontSize = 18.sp, textAlign = TextAlign.Justify, color = Color.Black, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp, horizontal = 15.dp))

                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp, horizontal = 35.dp), horizontalArrangement = Arrangement.Center){
                    Text(text ="Made by", fontWeight = FontWeight.Medium, fontSize = 17.sp, textAlign = TextAlign.Center, color = Color.Black)
                    Text(text =" Harmis Kishorbhai Tervadiya", fontWeight = FontWeight.Medium, fontSize = 17.sp, textAlign = TextAlign.Center, color = Orange)
                }


            }
            BackHandler {
                player.stop()
                player.release()
            }

        }
    )
}

