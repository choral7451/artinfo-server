import {Field, ObjectType} from "@nestjs/graphql";
import {User} from "@prisma/client";

@ObjectType()
export class UserResponse {
  @Field(() => String)
  id: string

  @Field(() => String)
  email: string

  @Field(() => String)
  nickname: string

  @Field(() => String , { nullable: true} )
  iconImageUrl?: string

  static fromUser(user: User) {
    const response = new UserResponse()
    response.id = user.id
    response.email = user.email
    response.nickname = user.nickname
    response.iconImageUrl = user.iconImageUrl ?? undefined;

    return response
  }
}