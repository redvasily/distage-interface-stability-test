package example

import distage._
import kafka.tools.StreamsResetter

// When trying to use there's a failure:
// scala.reflect.internal.Symbols$CyclicReference: illegal cyclic reference involving class InterfaceStability
object StreamsResetterModule extends ModuleDef {
  make[StreamsResetter]
}

object Example extends App {
  val injector = Injector()
  val plan = injector.plan(StreamsResetterModule, GCMode.NoGC)
  val locator = injector.produceUnsafe(plan)
  val streamsResetter = locator.get[StreamsResetter]
}

object Example2 extends App {
  // Crashes with the same exception:
  // scala.reflect.internal.Symbols$CyclicReference: illegal cyclic reference involving class InterfaceStability
  println(scala.reflect.runtime.universe.typeOf[kafka.tools.StreamsResetter])
}

object Example3 extends App {
  // This seems to work
  val info = izumi.fundamentals.reflection.macrortti.LTT[kafka.tools.StreamsResetter]
  println(info)
  println(info.repr)
}
