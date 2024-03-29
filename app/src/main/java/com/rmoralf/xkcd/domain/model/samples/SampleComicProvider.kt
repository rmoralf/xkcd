package com.rmoralf.xkcd.domain.model.samples

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rmoralf.xkcd.domain.model.Comic

class SampleComicProvider : PreviewParameterProvider<Comic> {
    override val values = sequenceOf(
        Comic(
            alt = "If you don't have an extension cord I can get that too.  Because we're friends!  Right?",
            day = "24",
            img = "https://imgs.xkcd.com/comics/woodpecker.png",
            link = "",
            month = "7",
            news = "",
            num = 614,
            safeTitle = "Woodpecker",
            title = "Woodpecker",
            transcript = "[[A man with a beret and a woman are standing on a boardwalk, leaning on a handrail.]]\\nMan: A woodpecker!\\n<<Pop pop pop>>\\nWoman: Yup.\\n\\n[[The woodpecker is banging its head against a tree.]]\\nWoman: He hatched about this time last year.\\n<<Pop pop pop pop>>\\n\\n[[The woman walks away.  The man is still standing at the handrail.]]\\n\\nMan: ... woodpecker?\\nMan: It's your birthday!\\n\\nMan: Did you know?\\n\\nMan: Did... did nobody tell you?\\n\\n[[The man stands, looking.]]\\n\\n[[The man walks away.]]\\n\\n[[There is a tree.]]\\n\\n[[The man approaches the tree with a present in a box, tied up with ribbon.]]\\n\\n[[The man sets the present down at the base of the tree and looks up.]]\\n\\n[[The man walks away.]]\\n\\n[[The present is sitting at the bottom of the tree.]]\\n\\n[[The woodpecker looks down at the present.]]\\n\\n[[The woodpecker sits on the present.]]\\n\\n[[The woodpecker pulls on the ribbon tying the present closed.]]\\n\\n((full width panel))\\n[[The woodpecker is flying, with an electric drill dangling from its feet, held by the cord.]]\\n\\n{{Title text: If you don't have an extension cord I can get that too.  Because we're friends!  Right?}}",
            year = "2009"
        )
    )
}