import {Field, ObjectType} from "@nestjs/graphql";
import {Comment, User} from "@prisma/client";

@ObjectType()
export class CommentResponse {
  @Field(() => String)
  id: string

  @Field(() => String, { nullable: true })
  tag?: string

  @Field(() => String)
  contents: string

  @Field(() => String)
  authorNickname: string

  @Field(() => Date)
  createdAt: Date

  static fromComment(comment: Comment & { user: User}): CommentResponse {
    const response = new CommentResponse()
    response.id = comment.id
    response.tag = comment.tag ?? undefined
    response.contents = comment.contents
    response.authorNickname = comment.user.nickname
    response.createdAt = comment.createdAt

    return response
  }
}