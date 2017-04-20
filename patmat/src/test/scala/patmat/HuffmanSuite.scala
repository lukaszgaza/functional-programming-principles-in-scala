package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
	trait TestTrees {
		val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
		val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
	}

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("should calculate number of offurences of letters in provided string") {
    val received = times(List('a', 'b', 'a')).sortBy(_._1)
    val expected = List(('a', 2), ('b', 1))

    assert(received === expected)
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton on list having multiple trees") {
    val l = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(!singleton(l))
  }

  test("singleton on list having one tree") {
    val l = List(Leaf('e', 1))
    assert(singleton(l))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("should create optimal CodeTree for provided text") {
    val text = "aababc"
    assert(createCodeTree(string2Chars(text)) === Fork(Fork(Leaf('c', 1), Leaf('b', 2), List('c', 'b'), 3), Leaf('a', 3), List('c', 'b', 'a'), 6))
  }

  test("decode word 'abba'") {
    val tree = Fork(Fork(Leaf('c', 1), Leaf('b', 2), List('c', 'b'), 3), Leaf('a', 3), List('c', 'b', 'a'), 6)
    val encoded = List(1, 0, 1, 0, 1, 1)
    assert(decode(tree, encoded) === List('a', 'b', 'b', 'a'))
  }

  test("decoded french secret") {
    assert(decodedSecret === List('h', 'u', 'f', 'f', 'm', 'a', 'n', 'e', 's', 't', 'c', 'o', 'o', 'l'))
  }

  test("encode 101011") {
    val tree = Fork(Fork(Leaf('c', 1), Leaf('b', 2), List('c', 'b'), 3), Leaf('a', 3), List('c', 'b', 'a'), 6)
    val decoded = List('a', 'b', 'b', 'a')
    assert(encode(tree)(decoded) === List(1, 0, 1, 0, 1, 1))
  }

  test("codeBits") {
    val codeTable = List(('a', List(1)), ('b', List(0, 1)), ('c', List(0, 0)))
    assert(codeBits(codeTable)('b') === List(0, 1))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("convert") {
    val tree = Fork(Fork(Leaf('c', 1), Leaf('b', 2), List('c', 'b'), 3), Leaf('a', 3), List('c', 'b', 'a'), 6)
    val codeTable = List(('a', List(1)), ('b', List(0, 1)), ('c', List(0, 0)))
    assert(convert(tree).sortBy(_._1) === codeTable)
  }

  test("merge code tables") {
    val codeTableOne = List(('a', List(1)), ('b', List(0, 1)), ('c', List(0, 0)))
    val codeTable = List(('a', List(1)), ('b', List(0, 1)), ('c', List(0, 0)))
  }

  test("quickEncode 101011") {
    val tree = Fork(Fork(Leaf('c', 1), Leaf('b', 2), List('c', 'b'), 3), Leaf('a', 3), List('c', 'b', 'a'), 6)
    val decoded = List('a', 'b', 'b', 'a')
    assert(quickEncode(tree)(decoded) === List(1, 0, 1, 0, 1, 1))
  }
}