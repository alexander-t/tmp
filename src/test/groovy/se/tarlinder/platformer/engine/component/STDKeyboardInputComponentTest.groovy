package se.tarlinder.platformer.engine.component

import se.tarlinder.platformer.engine.component.ai.AIComponent
import se.tarlinder.platformer.engine.component.camera.CameraComponent
import se.tarlinder.platformer.engine.component.input.KeyboardInputComponent
import se.tarlinder.platformer.engine.entity.MovingEntity
import se.tarlinder.platformer.engine.input.swing.KeyAdapterInputHandler
import se.tarlinder.platformer.mario.GameContext
import se.tarlinder.platformer.mario.Level
import se.tarlinder.platformer.mario.entity.Goomba
import spock.lang.Specification;

public class STDKeyboardInputComponentTest extends Specification {

    def "Three components are called during a Goomba's update"() {
        given:
        def aiComponentMock = Mock(AIComponent)
        def keyboardInputComponentMock = Mock(KeyboardInputComponent)
        def cameraComponentMock = Mock(CameraComponent)
        def goomba = new Goomba(0, 0, new GameContext(new Level(10, 10, [])))
                .withInputComponent(keyboardInputComponentMock)
                .withAIComponent(aiComponentMock)
                .withCameraComponent(cameraComponentMock)

        when:
        goomba.update()

        then:
        1 * aiComponentMock.update(goomba)
        (1.._) * keyboardInputComponentMock.update(_ as MovingEntity)
        (_..1) * cameraComponentMock.update(_)


    }
}
