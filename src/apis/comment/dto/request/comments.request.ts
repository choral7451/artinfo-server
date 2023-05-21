import {Field, InputType, Int} from "@nestjs/graphql";

@InputType()
export class CommentsRequest {
  @Field(() => String, { nullable: true })
  postId?: string;

  @Field(() => String,{ nullable: true })
  parentCommentId?: string;

  @Field(() => Int)
  countOfItems!: number;

  @Field(() => String, { nullable: true })
  lastItemId?: string;
}