import {Field, Int, ObjectType} from "@nestjs/graphql";
import {IssueType} from "@/apis/integration/enum/enum";
import {Issue, User} from "@prisma/client";

@ObjectType()
export class IssueResponse {
  @Field(() => String)
  id: string

  @Field(() => IssueType)
  type: IssueType

  @Field(() => String)
  title: string

  @Field(() => String)
  contents: string

  @Field(() => String)
  authorNickname: string

  @Field(() => String, { nullable: true })
  authorIconImageUrl?: string

  @Field(() => Int)
  countOfViews: number

  @Field(() => Date)
  createdAt: Date

  static fromIssue(issue: Issue & { user: User }): IssueResponse {
    const response = new IssueResponse()
    response.id = issue.id
    response.type = IssueType.ISSUE
    response.title = issue.title
    response.contents = issue.contents
    response.authorNickname = issue.user.nickname
    response.authorIconImageUrl = issue.user.iconImageUrl ?? undefined
    response.countOfViews = issue.countOfViews
    response.createdAt = issue.createdAt

    return response
  }
}