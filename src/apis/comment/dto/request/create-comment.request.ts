import {Field, InputType} from "@nestjs/graphql";

@InputType()
export class CreateCommentRequest {
  @Field(() => String)
  postId: string

  @Field(() => String, { nullable: true })
  parentCommentId?: string

  @Field(() => String, { nullable: true })
  tag?: string

  @Field(() => String)
  contents: string

  userId: string

  setUserId(userId: string): CreateCommentRequest {
    this.userId = userId

    return this
  }
}