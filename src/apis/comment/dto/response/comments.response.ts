import {Field, Int, ObjectType} from "@nestjs/graphql";
import {CommentResponse} from "@/apis/comment/dto/response/comment.response";
import {Comment, User} from "@prisma/client";

@ObjectType()
export class CommentsResponse {
  @Field(() => [CommentResponse])
  comments: CommentResponse[]

  @Field(() => Int)
  countOfTotal: number

  static fromComments(comments: (Comment & { user: User})[], countOfTotal: number):CommentsResponse {
    const response = new CommentsResponse()
    response.comments = comments.map(comment => CommentResponse.fromComment(comment))
    response.countOfTotal = countOfTotal

    return response
  }
}